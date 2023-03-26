
package com.dev.utils;

import com.dev.objects.Offer;
import com.dev.objects.Product;
import com.dev.objects.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Persist {

    private final SessionFactory sessionFactory;

    @Autowired
    public Persist(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public User getUserByUsername(String username) {
        User found = null;
        Session session = sessionFactory.openSession();
        found = (User) session.createQuery("FROM User WHERE username= :username")
                .setParameter("username", username)
                .uniqueResult();
        session.close();
        return found;
    }


    public void saveUser(User user) {
        Session session = sessionFactory.openSession();
        session.save(user);
        session.close();
    }

    public User getUserByUsernameAndToken(String username, String token) {
        User found = null;
        Session session = sessionFactory.openSession();
        found = (User) session.createQuery("FROM User WHERE username = :username " +
                        "AND token = :token")
                .setParameter("username", username)
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        return found;
    }

    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> allUsers = session.createQuery("FROM User ").list();
        session.close();
        return allUsers;
    }

    public List<Offer> getAllOffers() {
        Session session = sessionFactory.openSession();
        List<Offer> allOffers = session.createQuery("FROM Offer ").list();
        session.close();
        return allOffers;
    }

    public List<Offer> getOffersByUserAndProduct(User user, Product product) {
        Session session = sessionFactory.openSession();
        List<Offer> allOffers = session.createQuery("FROM Offer WHERE product=: product  AND offerFrom=:user").
                setParameter("product", product).setParameter("user", user).list();
        session.close();
        return allOffers;
    }


    public int getAmountOfOffersOnProduct(Integer productId) {
        Session session = sessionFactory.openSession();
        int allOffers = session.createQuery("FROM Offer WHERE product.id=: productId  ").
                setParameter("productId", productId).list().size();
        session.close();
        return allOffers;
    }

    public List<Offer> getOffersOnProduct(Integer productId) {
        Session session = sessionFactory.openSession();
        List<Offer> allOffers = session.createQuery("FROM Offer WHERE product.id=: productId  ").
                setParameter("productId", productId).list();
        session.close();
        return allOffers;
    }

    public List<Offer> getAmountOfOffersUserOnProduct(int productId) {
        Session session = sessionFactory.openSession();
        List<Offer> allUsersThatOffer = session.createQuery("FROM Offer WHERE product.id=: productId GROUP BY offerFrom ").
                setParameter("productId", productId).list();
        session.close();
        return allUsersThatOffer;
    }

    public void closeProduct(Integer productId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Product product = (Product) session.createQuery("FROM Product WHERE id=: productId  ").
                setParameter("productId", productId).uniqueResult();
        product.setOpen(false);
        session.update(product);
        tx.commit();
        session.close();
    }

    public void saveWinningBid(int offerId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Offer winningOffer = (Offer) session.createQuery("FROM Offer WHERE id=:offerId").
                setParameter("offerId", offerId).uniqueResult();
        winningOffer.setWinningOffer(true);
        session.update(winningOffer);
        tx.commit();
        session.close();
    }

    public void payCreditNewOffer(Integer senderId, Integer receiverId, double amountToPay) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User senderUser = (User) session.createQuery("FROM User WHERE id=:senderId").
                setParameter("senderId", senderId).uniqueResult();

        User receiverUser = (User) session.createQuery("FROM User WHERE id=:receiverId").
                setParameter("receiverId", receiverId).uniqueResult();

        User userAdmin = (User) session.createQuery("FROM User WHERE id= 1")
                .uniqueResult();
        userAdmin.setCredits(userAdmin.getCredits() + 1);
        senderUser.setCredits(senderUser.getCredits() - amountToPay - 1);
        receiverUser.setCredits(receiverUser.getCredits() + amountToPay);
        session.update(senderUser);
        session.update(receiverUser);
        session.update(userAdmin);
        tx.commit();
        session.close();
    }

    public void payBackLastHighestOffer(Integer senderId, Integer receiverId, double amountToPay) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User senderUser = (User) session.createQuery("FROM User WHERE id=:senderId").
                setParameter("senderId", senderId).uniqueResult();
        User receiverUser = (User) session.createQuery("FROM User WHERE id=:receiverId").
                setParameter("receiverId", receiverId).uniqueResult();

        senderUser.setCredits(senderUser.getCredits() - amountToPay);
        receiverUser.setCredits(receiverUser.getCredits() + amountToPay);
        session.update(senderUser);
        session.update(receiverUser);
        tx.commit();
        session.close();
    }


    public void closeProductPayCredit(Integer productOwnerId, double offerAmount) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User productOwner = (User) session.createQuery("FROM User WHERE id=:productOwnerId").
                setParameter("productOwnerId", productOwnerId).uniqueResult();

        User userAdmin = (User) session.createQuery("FROM User WHERE id= 1")
                .uniqueResult();
        userAdmin.setCredits(userAdmin.getCredits() + 0.05 * offerAmount);
        productOwner.setCredits(productOwner.getCredits() - 0.05 * offerAmount);
        session.update(productOwner);
        session.update(userAdmin);
        tx.commit();
        session.close();
    }

    public void fillCredit(Integer userId, double credit) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        User user = (User) session.createQuery("FROM User WHERE id=:userId").
                setParameter("userId", userId).uniqueResult();

        user.setCredits(credit);
        session.update(user);
        tx.commit();
        session.close();
    }


    public List<Product> getAllProducts() {
        Session session = sessionFactory.openSession();
        List<Product> allProducts = session.createQuery("FROM Product ").list();
        session.close();
        return allProducts;
    }

    //TODO: TAKE ONLY CLOSED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List<Offer> getAllClosedProducts() {
        Session session = sessionFactory.openSession();
        List<Offer> allClosedProductsOffers = session.createQuery(" FROM Offer  WHERE " +
                "product.isOpen = false " +
                " group by product ").list();
        session.close();
        return allClosedProductsOffers;
    }

    public int getAllOpenProductsById(Integer userId) {
        Session session = sessionFactory.openSession();
        List<Product> allOpenProducts = session.createQuery("FROM Product WHERE isOpen = true AND publisher.id= :userId")
                .setParameter("userId", userId)
                .list();
        int myProducts = allOpenProducts.size();
        session.close();
        return myProducts;
    }

    public List<Product> getAllOpenProducts() {
        Session session = sessionFactory.openSession();
        List<Product> allOpenProducts = session.createQuery("FROM Product WHERE isOpen = true ").list();
        session.close();
        return allOpenProducts;
    }

    public User getUserByToken(String token) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("FROM User WHERE token = :token")
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        return user;
    }

    public int getUserIdByToken(String token) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("FROM User WHERE token = :token")
                .setParameter("token", token)
                .uniqueResult();
        int userId = user.getId();
        session.close();
        return userId;
    }

    public User getUserById(Integer id) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("FROM User WHERE id = :id")
                .setParameter("id", id)
                .uniqueResult();
        session.close();
        return user;
    }

    public void saveProduct(Product product) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User userAdmin = (User) session.createQuery("FROM User WHERE id= 1")
                .uniqueResult();
        User userThatUpload = (User) session.createQuery("FROM User WHERE id= : user").setParameter("user", product.getPublisher().getId())
                .uniqueResult();
        userAdmin.setCredits(userAdmin.getCredits() + 2);
        userThatUpload.setCredits(userThatUpload.getCredits() - 2);
        session.update(userThatUpload);
        session.update(userAdmin);
        tx.commit();

        session.save(product);
        session.close();
    }

    public Product getProductById(Integer productId) {
        Session session = sessionFactory.openSession();
        Product product = (Product) session.createQuery("FROM Product WHERE id = :productId")
                .setParameter("productId", productId)
                .uniqueResult();
        session.close();
        return product;
    }

    public Offer getHighestOffer(Integer productId) {
        List<Offer> offers = null;
        Offer highestOffer = null;
        Session session = sessionFactory.openSession();
        offers = session.createQuery("FROM Offer WHERE product.id = :productId order by offerAmount desc ")
                .setParameter("productId", productId).list();
        if (offers.size() != 0) {
            highestOffer = offers.get(0);
        }
        session.close();
        return highestOffer;
    }

    public void saveOffer(Offer offer) {
        Session session = sessionFactory.openSession();
        session.save(offer);
        session.close();
    }

    public List<Integer> getUserOffersIdOnProduct(Integer productId) {
        Session session = sessionFactory.openSession();
        List<Integer> offersId =
                session.createQuery("SELECT DISTINCT offerFrom.id FROM Offer WHERE product.id =: productId").
                        setParameter("productId", productId).list();
        session.close();
        return offersId;
    }
}
