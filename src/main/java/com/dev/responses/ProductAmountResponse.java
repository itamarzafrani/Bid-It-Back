package com.dev.responses;

public class ProductAmountResponse extends BasicResponse{

        private int productsAmount;

        public ProductAmountResponse(int productsAmount) {
            this.productsAmount = productsAmount;
        }

        public ProductAmountResponse(boolean success, Integer errorCode, int productsAmount) {
            super(success, errorCode);
            this.productsAmount = productsAmount;
        }

        public int getProductsAmount() {
            return productsAmount;
        }

        public void setProductsAmount(int productsAmount) {
            this.productsAmount = productsAmount;
        }

}
