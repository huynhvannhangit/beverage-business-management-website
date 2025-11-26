# ☕ Website Quản Lý Kinh Doanh Đồ Uống

## ✨ Giới Thiệu Dự Án

Đây là dự án xây dựng **Hệ thống Quản lý Kinh doanh Đồ uống** dưới dạng một website, được phát triển trên nền tảng **Java Spring Boot**. Hệ thống nhằm mục đích tự động hóa và tối ưu hóa các quy trình quản lý tại một cửa hàng đồ uống (như quán cà phê, trà sữa...).

Ứng dụng hỗ trợ đa vai trò người dùng (Quản lý, Thu ngân, Phục vụ, Pha chế), cung cấp các chức năng toàn diện từ order, pha chế, thanh toán đến quản lý danh mục, nhân sự và báo cáo doanh thu.

---

## 🚀 Công Nghệ và Dependencies Sử Dụng

Dự án được xây dựng trên công nghệ chính là **Java 21** và **Spring Boot 3.x**, quản lý bởi **Maven**.

| Lĩnh vực | Dependency / Công nghệ | Mô tả chính |
|----------|------------------------|-------------|
| **Backend Core** | `spring-boot-starter-web` | Xây dựng ứng dụng web. |
| **Ngôn ngữ** | Java 21 (LTS) | |
| **Template Engine** | `spring-boot-starter-thymeleaf` | Render giao diện phía Server. |
| **ORM & Database** | `spring-boot-starter-data-jpa` | Tích hợp Hibernate để quản lý cơ sở dữ liệu. |
| **CSDL** | MySQL (`mysql-connector-j`) | Hệ quản trị cơ sở dữ liệu quan hệ. |
| **Bảo mật** | `spring-boot-starter-security` | Quản lý xác thực, phân quyền người dùng. |
| **Validation** | `spring-boot-starter-validation` | Thẩm định dữ liệu đầu vào. |
| **Database Migration** | `flyway-core`, `flyway-mysql` | Quản lý phiên bản schema database. |
| **Monitoring** | `spring-boot-starter-actuator` | Giám sát và quản lý ứng dụng (Health, Info...). |
| **Email** | `spring-boot-starter-mail` | Hỗ trợ chức năng gửi email (ví dụ: khôi phục mật khẩu). |
| **Utility** | `lombok` | Giảm thiểu boilerplate code. |

---

## 🛠️ Yêu Cầu Hệ Thống

Để chạy ứng dụng, bạn cần cài đặt các phần mềm sau:

- **Java Development Kit (JDK)**: Phiên bản 21 trở lên.
- **MySQL Server**: Phiên bản 8.0 trở lên.
- **Công cụ Build**: Apache Maven.

---

## ⚙️ Hướng Dẫn Cài Đặt và Khởi Chạy

### 1. Clone Source Code

```bash
git clone https://github.com/huynhvannhangit/beverage-business-management.git
cd beverage-business-management
```

### 2. Cấu hình Cơ sở dữ liệu

Dự án sử dụng **Flyway** để quản lý schema. Vui lòng tạo một CSDL trống trong MySQL, ví dụ: `beverage_business_management`.

Cập nhật thông tin kết nối (username, password) trong file `src/main/resources/application.yml` (profile `dev`). Flyway sẽ tự động tạo các bảng cần thiết.

### 3. Build và Chạy Ứng Dụng (Sử dụng Maven)

```bash
# 1. Build dự án (bao gồm việc tải dependencies)
mvn clean install

# 2. Chạy ứng dụng Spring Boot (profile mặc định là 'dev')
mvn spring-boot:run
```

Ứng dụng sẽ có sẵn tại địa chỉ: **http://localhost:8080/beverage-business-management**.

---

## 🔑 Phân Quyền và Chức Năng Chính

Hệ thống được thiết kế với 4 nhóm người dùng dựa trên sơ đồ Use Case:

| Vai trò | Chức năng Quản lý | Chức năng Vận hành |
|---------|-------------------|-------------------|
| **👑 Quản lý - Chủ Quán** | Quản lý Nhân viên, Tài khoản. Quản lý Sản phẩm, Danh mục, Khu vực, Bàn. | Quản lý Hóa đơn (Hủy, Thống kê Doanh thu). |
| **💵 Nhân Viên Thu Ngân** | Quản lý Tài khoản cá nhân. | Thanh toán, Giảm giá, In, Tìm kiếm Hóa đơn. |
| **🧍 Nhân Viên Phục Vụ** | Quản lý Tài khoản cá nhân. | Nhập Đơn Hàng (Order), Chuyển/Gộp Bàn, Xem sản phẩm. |
| **👨‍🍳 Nhân Viên Pha Chế** | Quản lý Tài khoản cá nhân. | Xem Danh Sách Đơn Cần Pha Chế, Cập nhật trạng thái pha chế. |

---

## 🔒 Ghi Chú về Cấu Hình Môi Trường

Dự án sử dụng **Spring Profiles** (`dev` và `prod`) được định nghĩa trong `application.yml`. Lưu ý quan trọng:

- **Môi trường `dev`**: Hiển thị SQL, tắt cache, và có tài khoản test mặc định (`admin/admin123`).
- **Môi trường `prod`**: Tắt hiển thị SQL, bật cache, và sử dụng mật khẩu/thông tin kết nối lấy từ **Biến Môi Trường** để đảm bảo bảo mật.

---

## 🤝 Đóng Góp

Mọi đóng góp để cải thiện dự án đều được hoan nghênh. Vui lòng gửi **Pull Request** tới repository.

---

## ✉️ Liên Hệ

- **Tên Dự Án**: Website Quản Lý Kinh Doanh Đồ Uống
- Link Repository: [https://github.com/huynhvannhangit/beverage-business-management.git](https://github.com/huynhvannhangit/beverage-business-management.git)
- **Tác Giả**: Huỳnh Văn Nhãn
- **Email Liên Hệ**: huynhvannhanwork@gmail.com

---

⭐ Nếu bạn thấy dự án hữu ích, hãy cho một **Star** trên GitHub! ⭐

