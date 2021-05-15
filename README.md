# LTDD_CS81_Nhom22
Ứng dụng dự báo thời tiết trên thiết bị android
### Mô tả kĩ thuật
* Ứng dụng được thiết kế giao diện tham khảo từ nhiều nguồn tìm kiếm trên google
* Ứng dụng được viết bằng ngôn ngữ Java
* Để có thể hiển thị lên thông tin về thời tiết trong ngày và 7 ngày tiếp theo ứng dụng đã sử dụng API từ https://openweathermap.org/
* Các API được gọi từ openweathermap.org bao gồm *Current Weather Data* và *One Call API*
* Ứng dụng sử dụng dependency **Retrofit** để chuyển đổi API thành java interface
* Ứng dụng sử dụng SqliteAssetHelper để truy cập vào database là danh sách các thành phố sử dụng để gọi API 
(Do việc sử dụng API của google map gặp một số vấn đề liên quan đến credit card)
* Ứng dụng còn sử dụng thêm 1 vài dependency khác để hiển thị giao diện
* Ứng dụng vẫn sẽ còn tồn đọng nhiều lỗi chưa giải quyết.

### Hướng dẫn sử dụng
* Cần kết nối INTERNET để có thể sử dụng ứng dụng
* Ứng dụng có thể xem được thời tiết trong 7 ngày tiếp theo và thời tiết trong 48 giờ tiếp theo
* Mặc định ban đầu ứng dụng sẽ lấy thông tin thời tiết từ Thành Phố Hồ Chí Minh
* Có thể thêm các thành phố khác bằng việc nhấn chọn **add city** trên màn hình
* Nhập tên thành phố mà bạn muốn biết thông tin và thành phố sẽ được thêm vào màn hình của ứng dụng
* Chọn thành phố mà bạn muốn xem chi tiết và thông tin chi tiết thời tiết của thành phố sẽ xuất hiện
* Để xóa thành phố bạn thêm vào hãy nhấn giữ chọn thành phố và chọn delete.



### Thành viên thực hiện
Bao gồm:

1. **Phạm Quang Minh:** Thực hiện xử lý dữ liệu, hiển thị dữ liệu lên giao diện, sửa lỗi
2. **Tất Quảng Kiệt:** Thiết kế giao diện phần MainActivity và xử lý việc thêm layout, lấy dữ liệu được nhập từ người dùng.
3. **Lê Công Nhật:** Thiết kế giao diện phần Detail, chi tiết thời tiết của khu vực người dùng chọn
> Thông tin chi tiết trong file word đính kèm
