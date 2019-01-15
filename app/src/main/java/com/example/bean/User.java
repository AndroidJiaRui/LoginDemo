package com.example.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id(autoincrement = true)
        private long userId;
        private int status;
        private String headPic;
        private String nickName;
        private String phone;
        private String sessionId;
        private int sex;


        @Generated(hash = 594528590)
        public User(long userId, int status, String headPic, String nickName,
                String phone, String sessionId, int sex) {
            this.userId = userId;
            this.status = status;
            this.headPic = headPic;
            this.nickName = nickName;
            this.phone = phone;
            this.sessionId = sessionId;
            this.sex = sex;
        }

        @Generated(hash = 586692638)
        public User() {
        }


        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
}
