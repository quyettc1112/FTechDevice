package com.example.ftechdevice.Model;

import java.util.List;

public class PostOrder {
        private String title;
        private String description;
        private int userId;
        private int status;
        private List<OrderDetailModel> orderDetails;

        public PostOrder(String title, String description, int userId, int status, List<OrderDetailModel> orderDetails) {
            this.title = title;
            this.description = description;
            this.userId = userId;
            this.status = status;
            this.orderDetails = orderDetails;
        }
}
