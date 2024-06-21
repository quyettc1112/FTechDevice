package com.example.ftechdevice.Common.Constants;

import com.example.ftechdevice.Common.CommonAdapter.CategoryOptionAdapter;
import com.example.ftechdevice.Common.CommonAdapter.VideoMainAdapter;
import com.example.ftechdevice.Model.CartModel;
import com.example.ftechdevice.Model.ToyModel;
import com.example.ftechdevice.Model.VideoModel;
import com.example.ftechdevice.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String DEFAULT_ERROR_MESSAGE = "An error occurred";
    // Nice Bottom Nav Bar
    public static final String ITEM_TAG = "item";
    public static final String ICON_ATTRIBUTE = "icon";
    public static final String TITLE_ATTRIBUTE = "title";
    public static final String WHITE_COLOR_HEX = "#FFFFFF";

    public static final String DEFAULT_INDICATOR_COLOR = "#426dfe";
    public static final String DEFAULT_TEXT_COLOR = "#444444";
    public static final String DEFAULT_TEXT_COLOR_ACTIVE = "#426dfe";

    public static final String DEFAULT_PRIMARY_COLOR = "#FF4949";
    public static final String DEFAULT_PRIMARY_COLOR_ACTIVE = "#C9C9C9";
    public static final String IS_BACK_FROM_MAP = "IS_BACK_FROM_MAPs";
    public static List<VideoMainAdapter.Course> getListCourse() {
        return Arrays.asList(
                new VideoMainAdapter.Course(
                        "Linh kiện giá rẻ nhưng vô cùng chất lượng",
                        R.drawable.banner_logo,
                        4.5,
                        4242,
                        400000.0,
                        350000.0
                ),
                new VideoMainAdapter.Course(
                        "Mừng đại lễ, khuyến mãi lớn lên đến 55%",
                        R.drawable.banner_xakho,
                        4.5,
                        4242,
                        400000.0,
                        350000.0
                ),
                new VideoMainAdapter.Course(
                        "Màn hình nét căng đét nhưng giá cực chill",
                        R.drawable.banner_manhinh,
                        4.5,
                        4242,
                        400000.0,
                        350000.0
                ),
                new VideoMainAdapter.Course(
                        "Tuần lễ điện thoại, mua ngay!!!",
                        R.drawable.banner_dienthoai,
                        4.5,
                        4242,
                        400000.0,
                        350000.0
                )
        );
    }

    public static List<CategoryOptionAdapter.CategoryString> getListString() {
        return Arrays.asList(
                new CategoryOptionAdapter.CategoryString("Tất Cả"),
                new CategoryOptionAdapter.CategoryString("Mới Nhất"),
                new CategoryOptionAdapter.CategoryString("Phổ Biến"),
                new CategoryOptionAdapter.CategoryString("Đặt Biệt")
        );
    }

    public static List<ToyModel> getListToys() {
        return Arrays.asList(
                new ToyModel(
                        0,
                        "Bộ xếp hình nam châm 72 chi tiết",
                        R.drawable.ic_image_toy_01,
                        "Khám phá bộ đồ chơi mới với bé yêu BỘ XẾP HÌNH NAM CHÂM 72 CHI TIẾT Với Bộ Xếp Hình Nam Châm, bé sẽ học được gì? - Phát triển tư duy sáng tạo thông qua việc tạo ra nhiều hình khối đa dạng, từ các hình dạng que đến hình tròn. - Tăng cường khả năng quan sát và phát triển khả năng tưởng tượng không gian thông",
                        4.5,
                        true,
                        155000.0,
                        1,
                        new ToyModel.CategoryModel(
                                1,
                                "Trẻ Em",
                                R.drawable.ic_teddy
                        ),
                        new ToyModel.SellerInfoModel(
                                1,
                                "Đạt Võ",
                                "quyettc@gmail.com",
                                "0356790686"
                        ),
                        Arrays.asList(R.drawable.ic_image_toy_01, R.drawable.ic_image_1_2jpg, R.drawable.ic_image_1_3),
                        "ALL"
                ),
                new ToyModel(
                        1,
                        "8 CUỐN SÁCH VẢI MINI NHIỀU CHỦ ĐỀ",
                        R.drawable.ic_image_toy_02,
                        "Khám phá bộ đồ chơi mới với bé yêu BỘ XẾP HÌNH NAM CHÂM 72 CHI TIẾT Với Bộ Xếp Hình Nam Châm, bé sẽ học được gì? - Phát triển tư duy sáng tạo thông qua việc tạo ra nhiều hình khối đa dạng, từ các hình dạng que đến hình tròn. - Tăng cường khả năng quan sát và phát triển khả năng tưởng tượng không gian thông",
                        4.5,
                        true,
                        155000.0,
                        1,
                        new ToyModel.CategoryModel(
                                1,
                                "Trẻ Em",
                                R.drawable.ic_teddy
                        ),
                        new ToyModel.SellerInfoModel(
                                1,
                                "Đạt Võ",
                                "quyettc@gmail.com",
                                "0356790686"
                        ),
                        Arrays.asList(R.drawable.ic_material_toy, R.drawable.ic_material_toy, R.drawable.ic_material_toy),
                        "ALL"
                ),
                new ToyModel(
                        2,
                        "Bộ xếp hình người thăng bằng",
                        R.drawable.ic_image_03,
                        "ĐỒ CHƠI XẾP THĂNG BẰNG HÌNH NGƯỜI RẠP XIẾC VUI NHỘN Đồ Chơi Xếp Thăng Bằng với 32 nhân vật, 32 sticker đôi và 4 quả bóng sẽ mang lại những giờ phút vui vẻ cho bé và gia đình. ",
                        4.5,
                        true,
                        99000.0,
                        2,
                        new ToyModel.CategoryModel(
                                2,
                                "Giải Đố",
                                R.drawable.ic_teddy
                        ),
                        new ToyModel.SellerInfoModel(
                                1,
                                "Đạt Võ",
                                "quyettc@gmail.com",
                                "0356790686"
                        ),
                        Arrays.asList(R.drawable.ic_material_toy, R.drawable.ic_material_toy, R.drawable.ic_material_toy),
                        "ALL"
                ),
                new ToyModel(
                        3,
                        "Mô hình con vật 58 chi tiết",
                        R.drawable.ic_iamge_mo_hinh_con_vat,
                        "Đồ chơi trẻ em vip pro",
                        4.5,
                        true,
                        199000.0,
                        1,
                        new ToyModel.CategoryModel(
                                2,
                                "Giải Đố",
                                R.drawable.ic_teddy
                        ),
                        new ToyModel.SellerInfoModel(
                                1,
                                "Đạt Võ",
                                "quyettc@gmail.com",
                                "0356790686"
                        ),
                        Arrays.asList(R.drawable.ic_material_toy, R.drawable.ic_material_toy, R.drawable.ic_material_toy),
                        "ALL"
                ),
                new ToyModel(
                        4,
                        "Rối ngón tay gia đình 6 con",
                        R.drawable.ic_image_6_ngon,
                        "Đồ chơi trẻ em vip pro",
                        4.5,
                        true,
                        79000.0,
                        1,
                        new ToyModel.CategoryModel(
                                1,
                                "Robot",
                                R.drawable.ic_teddy
                        ),
                        new ToyModel.SellerInfoModel(
                                1,
                                "Đạt Võ",
                                "quyettc@gmail.com",
                                "0356790686"
                        ),
                        Arrays.asList(R.drawable.ic_material_toy, R.drawable.ic_material_toy, R.drawable.ic_material_toy),
                        "ALL"
                ),
                new ToyModel(
                        5,
                        "Đồng hồ nước màu sắc kích thích thị giác",
                        R.drawable.ic_image_dong_ho,
                        "Đồ chơi trẻ em vip pro",
                        4.5,
                        true,
                        49000.0,
                        1,
                        new ToyModel.CategoryModel(
                                1,
                                "Robot",
                                R.drawable.ic_teddy
                        ),
                        new ToyModel.SellerInfoModel(
                                1,
                                "Đạt Võ",
                                "quyettc@gmail.com",
                                "0356790686"
                        ),
                        Arrays.asList(R.drawable.ic_material_toy, R.drawable.ic_material_toy, R.drawable.ic_material_toy),
                        "ALL"
                ),
                new ToyModel(
                        6,
                        "Bóng bóp tay màu sắc vui nhộn",
                        R.drawable.ic_image_bong_bong,
                        "Đồ chơi trẻ em vip pro",
                        4.5,
                        true,
                        35000.0,
                        1,
                        new ToyModel.CategoryModel(
                                1,
                                "Phương Tiện",
                                R.drawable.ic_teddy
                        ),
                        new ToyModel.SellerInfoModel(
                                1,
                                "Đạt Võ",
                                "quyettc@gmail.com",
                                "0356790686"
                        ),
                        Arrays.asList(R.drawable.ic_material_toy, R.drawable.ic_material_toy, R.drawable.ic_material_toy),
                        "ALL"
                ),
                new ToyModel(
                        7,
                        "Xe gỗ trượt cao tốc vui nhộn",
                        R.drawable.ic_xe_go,
                        "Đồ chơi trẻ em vip pro",
                        4.5,
                        true,
                        165000.0,
                        1,
                        new ToyModel.CategoryModel(
                                1,
                                "Phương Tiện",
                                R.drawable.ic_teddy
                        ),
                        new ToyModel.SellerInfoModel(
                                1,
                                "Đạt Võ",
                                "quyettc@gmail.com",
                                "0356790686"
                        ),
                        Arrays.asList(R.drawable.ic_material_toy, R.drawable.ic_material_toy, R.drawable.ic_material_toy),
                        "ALL"
                )
        );
    }

    public static ToyModel getListToysId(int id) {
        return getListToys().get(id);
    }

    public static List<VideoModel> getListVideos() {
        return Arrays.asList(
                new VideoModel("czbB8AQsBzU"),
                new VideoModel("UFAKw0wgcjA"),
                new VideoModel("kv26Mw4l8jg"),
                new VideoModel("hn_teOwL_iA"),
                new VideoModel("3-gjAL20WGE"),
                new VideoModel("9mL-TS0gcmA")
        );
    }

    public static ArrayList<CartModel> getListCart() {
        ToyModel toyModel = getListToys().get(0);
        ToyModel toyModel2 = getListToys().get(1);
        ToyModel toyModel3 = getListToys().get(2);
        return new ArrayList<>(
                Arrays.asList(
                        CartModel.create(toyModel, 2),
                        CartModel.create(toyModel2, 2),
                        CartModel.create(toyModel3, 2)
                )
        );
    }
}
