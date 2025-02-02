package com.example.ftechdevice.Model.CartModule;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.ftechdevice.Model.ProductModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CartResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("user")
    private User user;

    @SerializedName("product")
    private Product product;

    @SerializedName("quantity")
    private int quantity;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static class User implements Parcelable{
        @SerializedName("id")
        private int id;

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", role=" + role +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    ", isActive=" + isActive +
                    ", avatar='" + avatar + '\'' +
                    ", enabled=" + enabled +
                    ", authorities=" + authorities +
                    ", accountNonExpired=" + accountNonExpired +
                    ", accountNonLocked=" + accountNonLocked +
                    ", credentialsNonExpired=" + credentialsNonExpired +
                    '}';
        }

        protected User(Parcel in) {
            id = in.readInt();
            username = in.readString();
            email = in.readString();
            password = in.readString();
            fullName = in.readString();
            phone = in.readString();
            address = in.readString();
            isActive = in.readByte() != 0;
            avatar = in.readString();
            enabled = in.readByte() != 0;
            accountNonExpired = in.readByte() != 0;
            accountNonLocked = in.readByte() != 0;
            credentialsNonExpired = in.readByte() != 0;
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<Authority> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(List<Authority> authorities) {
            this.authorities = authorities;
        }

        public boolean isAccountNonExpired() {
            return accountNonExpired;
        }

        public void setAccountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
        }

        public boolean isAccountNonLocked() {
            return accountNonLocked;
        }

        public void setAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
        }

        public boolean isCredentialsNonExpired() {
            return credentialsNonExpired;
        }

        public void setCredentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
        }

        @SerializedName("role")
        private Role role;

        @SerializedName("username")
        private String username;

        @SerializedName("email")
        private String email;

        @SerializedName("password")
        private String password;

        @SerializedName("fullName")
        private String fullName;

        @SerializedName("phone")
        private String phone;

        @SerializedName("address")
        private String address;

        @SerializedName("isActive")
        private boolean isActive;

        @SerializedName("avatar")
        private String avatar;

        @SerializedName("enabled")
        private boolean enabled;

        @SerializedName("authorities")
        private List<Authority> authorities;

        @SerializedName("accountNonExpired")
        private boolean accountNonExpired;

        @SerializedName("accountNonLocked")
        private boolean accountNonLocked;

        @SerializedName("credentialsNonExpired")
        private boolean credentialsNonExpired;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(username);
            dest.writeString(email);
            dest.writeString(password);
            dest.writeString(fullName);
            dest.writeString(phone);
            dest.writeString(address);
            dest.writeByte((byte) (isActive ? 1 : 0));
            dest.writeString(avatar);
            dest.writeByte((byte) (enabled ? 1 : 0));
            dest.writeByte((byte) (accountNonExpired ? 1 : 0));
            dest.writeByte((byte) (accountNonLocked ? 1 : 0));
            dest.writeByte((byte) (credentialsNonExpired ? 1 : 0));
        }

        // Getters and Setters
        // ...
    }

    public static class Role {
        @SerializedName("id")
        private int id;

        @SerializedName("isActive")
        private boolean isActive;

        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
// Getters and Setters
        // ...
    }

    public static class Authority {
        @SerializedName("authority")
        private String authority;

        // Getters and Setters

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }

        // ...
    }

    public static class Product  implements Parcelable {

        public Product(int id, String name, String description, double price, int quantity, String imageUrl, boolean isActive, ProductModel.ProductCategory productCategory) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
            this.isActive = isActive;
            this.productCategory = productCategory;
        }

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("price")
        private double price;

        @SerializedName("quantity")
        private int quantity;

        @SerializedName("imageUrl")
        private String imageUrl;

        @SerializedName("isActive")
        private boolean isActive;

        @SerializedName("productCategory")
        private ProductModel.ProductCategory productCategory;

        protected Product(Parcel in) {
            id = in.readInt();
            name = in.readString();
            description = in.readString();
            price = in.readDouble();
            quantity = in.readInt();
            imageUrl = in.readString();
            isActive = in.readByte() != 0;
            productCategory = in.readParcelable(ProductModel.ProductCategory.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeString(description);
            dest.writeDouble(price);
            dest.writeInt(quantity);
            dest.writeString(imageUrl);
            dest.writeByte((byte) (isActive ? 1 : 0));
            dest.writeParcelable(productCategory, flags);
        }


        public static final Creator<Product> CREATOR = new Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };

        // Getters and Setters

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", price=" + price +
                    ", quantity=" + quantity +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", isActive=" + isActive +
                    ", productCategory=" + productCategory +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public ProductModel.ProductCategory getProductCategory() {
            return productCategory;
        }

        public void setProductCategory(ProductModel.ProductCategory productCategory) {
            this.productCategory = productCategory;
        }

        @Override
        public int describeContents() {
            return 0;
        }



// ...
    }


}