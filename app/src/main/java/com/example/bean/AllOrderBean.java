package com.example.bean;

import java.util.List;

public class AllOrderBean {
            /**
             * commentStatus : 1
             * commodityCount : 2
             * commodityId : 27
             * commodityName : 休闲马衔扣保暖绒里棉鞋懒人鞋毛毛鞋平底女雪地靴女短靴子豆豆鞋女鞋
             * commodityPic : http://172.17.8.100/images/small/commodity/nx/ddx/3/1.jpg,http://172.17.8.100/images/small/commodity/nx/ddx/3/2.jpg,http://172.17.8.100/images/small/commodity/nx/ddx/3/3.jpg,http://172.17.8.100/images/small/commodity/nx/ddx/3/4.jpg,http://172.17.8.100/images/small/commodity/nx/ddx/3/5.jpg
             * commodityPrice : 139
             * orderDetailId : 772
             */

            private int commentStatus;
            private int commodityCount;
            private int commodityId;
            private String commodityName;
            private String commodityPic;
            private int commodityPrice;
            private int orderDetailId;

            public int getCommentStatus() {
                return commentStatus;
            }

            public void setCommentStatus(int commentStatus) {
                this.commentStatus = commentStatus;
            }

            public int getCommodityCount() {
                return commodityCount;
            }

            public void setCommodityCount(int commodityCount) {
                this.commodityCount = commodityCount;
            }

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public String getCommodityPic() {
                return commodityPic;
            }

            public void setCommodityPic(String commodityPic) {
                this.commodityPic = commodityPic;
            }

            public int getCommodityPrice() {
                return commodityPrice;
            }

            public void setCommodityPrice(int commodityPrice) {
                this.commodityPrice = commodityPrice;
            }

            public int getOrderDetailId() {
                return orderDetailId;
            }

            public void setOrderDetailId(int orderDetailId) {
                this.orderDetailId = orderDetailId;
            }
}
