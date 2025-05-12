-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost
-- Thời gian đã tạo: Th5 12, 2025 lúc 03:21 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `travel_tour`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bookings`
--

CREATE TABLE `bookings` (
  `id` bigint(20) NOT NULL,
  `booking_date` datetime(6) NOT NULL,
  `number_of_participants` int(11) DEFAULT NULL,
  `special_requirements` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `total_price` decimal(38,2) NOT NULL,
  `tour_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `bookings`
--

INSERT INTO `bookings` (`id`, `booking_date`, `number_of_participants`, `special_requirements`, `status`, `total_price`, `tour_id`, `user_id`) VALUES
(1, '2025-03-18 07:04:38.000000', 12, NULL, 'CANCELLED', 4799.88, 2, 2),
(2, '2025-03-18 07:05:16.000000', 12, NULL, 'CONFIRMED', 4799.88, 2, 2),
(3, '2025-03-18 07:07:30.000000', 5, NULL, 'CANCELLED', 1999.95, 2, 2),
(4, '2025-03-18 07:15:25.000000', 6, NULL, 'CANCELLED', 1799.94, 1, 2),
(5, '2025-03-18 07:21:26.000000', 5, NULL, 'CANCELLED', 1499.95, 1, 2),
(6, '2025-03-18 07:22:01.000000', 5, NULL, 'CONFIRMED', 1499.95, 1, 2),
(7, '2025-03-18 10:57:16.000000', 5, NULL, 'CANCELLED', 999.95, 3, 2),
(8, '2025-03-18 10:57:24.000000', 5, NULL, 'CANCELLED', 999.95, 3, 2),
(9, '2025-03-18 10:57:32.000000', 5, NULL, 'CANCELLED', 999.95, 3, 2),
(10, '2025-03-18 10:57:40.000000', 5, NULL, 'CANCELLED', 999.95, 3, 2),
(11, '2025-03-18 10:57:56.000000', 3, NULL, 'CANCELLED', 899.97, 1, 2),
(12, '2025-03-18 11:02:57.000000', 5, NULL, 'CANCELLED', 999.95, 3, 2),
(13, '2025-03-18 11:04:10.000000', 5, NULL, 'CANCELLED', 999.95, 3, 2),
(14, '2025-03-18 11:10:58.000000', 5, NULL, 'CANCELLED', 999.95, 3, 2),
(15, '2025-03-18 11:13:37.000000', 5, NULL, 'CANCELLED', 999.95, 3, 2),
(16, '2025-03-18 11:15:49.000000', 5, NULL, 'CANCELLED', 995.00, 4, 2),
(17, '2025-03-18 11:18:28.000000', 4, NULL, 'CANCELLED', 1199.96, 1, 2),
(18, '2025-03-18 11:26:09.000000', 4, NULL, 'CANCELLED', 796.00, 4, 2),
(19, '2025-03-18 11:35:48.000000', 4, NULL, 'CANCELLED', 799.96, 3, 2),
(20, '2025-03-18 11:39:16.000000', 2, NULL, 'CANCELLED', 599.98, 1, 2),
(21, '2025-03-18 11:41:31.000000', 3, NULL, 'CONFIRMED', 599.97, 3, 2),
(22, '2025-03-18 11:43:59.000000', 4, NULL, 'CANCELLED', 796.00, 4, 2),
(24, '2025-03-19 14:54:10.000000', 3, NULL, 'CONFIRMED', 1497.00, 6, 3),
(25, '2025-03-20 12:00:07.000000', 5, NULL, 'CANCELLED', 1499.95, 1, 3),
(26, '2025-03-20 12:15:01.000000', 10, NULL, 'CANCELLED', 2999.90, 1, 3),
(27, '2025-03-20 12:18:28.000000', 10, NULL, 'CONFIRMED', 2699.91, 1, 3),
(28, '2025-04-22 13:45:12.000000', 4, NULL, 'PENDING', 1599.96, 2, 2),
(29, '2025-04-22 13:55:58.000000', 1, NULL, 'PENDING', 199.99, 3, 2),
(30, '2025-04-23 10:56:24.000000', 4, NULL, 'PENDING', 1599.96, 2, 3),
(31, '2025-04-23 10:59:03.000000', 2, NULL, 'PENDING', 998.00, 6, 4),
(32, '2025-04-25 13:38:57.000000', 3, NULL, 'PENDING', 809.97, 1, 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ratings`
--

CREATE TABLE `ratings` (
  `id` bigint(20) NOT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `stars` int(11) NOT NULL,
  `booking_id` bigint(20) NOT NULL,
  `tour_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ratings`
--

INSERT INTO `ratings` (`id`, `comment`, `created_at`, `stars`, `booking_id`, `tour_id`, `user_id`) VALUES
(1, 'Good tour, great experience', '2025-03-25 07:03:21.000000', 5, 21, 3, 2),
(2, 'The best tour ever.', '2025-03-25 07:05:01.000000', 5, 2, 2, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `reviews`
--

CREATE TABLE `reviews` (
  `id` bigint(20) NOT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `rating` int(11) NOT NULL,
  `verified` bit(1) NOT NULL,
  `tour_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `reviews`
--

INSERT INTO `reviews` (`id`, `comment`, `created_at`, `rating`, `verified`, `tour_id`, `user_id`) VALUES
(3, 'Good tour', '2025-03-24 15:01:04.000000', 5, b'1', 6, 3),
(4, 'Good Tour ever\r\n', '2025-03-24 15:01:55.000000', 5, b'1', 1, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tours`
--

CREATE TABLE `tours` (
  `id` bigint(20) NOT NULL,
  `available` bit(1) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `max_participants` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `start_date` date DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `available_seats` int(11) NOT NULL,
  `average_rating` double DEFAULT NULL,
  `total_reviews` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tours`
--

INSERT INTO `tours` (`id`, `available`, `description`, `destination`, `duration`, `end_date`, `image_url`, `max_participants`, `name`, `price`, `start_date`, `discount`, `available_seats`, `average_rating`, `total_reviews`) VALUES
(1, b'1', 'Explore the stunning limestone islands and emerald waters of Ha Long Bay, a UNESCO World Heritage site. Enjoy luxury cruise accommodation, kayaking, cave exploration, and fresh seafood.', 'Ha Long Bay, Vietnam', '3 days 2 nights', '2025-03-31', '/images/1742472804588_vinhhalong.jpg', 20, 'Beautiful Ha Long Bay', 299.99, '2025-03-20', 10, 0, 5, 1),
(2, b'1', 'Experience the breathtaking mountain landscapes and rich cultural heritage of Sapa. Trek through terraced rice fields, visit ethnic minority villages, and stay with local families.', 'Sapa, Vietnam', '4 days 3 nights', '2025-03-26', '/images/1742472475488_sapa.jpg', 15, 'Sapa Mountain Trek', 399.99, '2025-03-21', NULL, 0, NULL, NULL),
(3, b'1', 'Discover the rich culture and daily life of the Mekong Delta. Visit floating markets, fruit orchards, and traditional villages while enjoying local cuisine.', 'Mekong Delta, Vietnam', '2 days 1 night', '2025-04-09', '/images/mekongdelta.jpg', 25, 'Mekong Delta Experience', 199.99, '2025-04-08', NULL, 0, NULL, NULL),
(4, b'1', 'Beautiful beach with great views', 'Nha Trang', '3 days 2 night', '2025-03-29', '/images/1742299378037_nha-trang.jpg', 10, 'Nha Trang City', 199.00, '2025-03-21', NULL, 0, NULL, NULL),
(6, b'1', 'Embark on an unforgettable journey through the Hà Giang Loop, one of Vietnam’s most spectacular travel routes.', 'Ha Giang, Viet Nam', '3 days 2 night', '2025-04-10', '/images/1742310771917_hagiang.jpg', 10, 'Ha Giang loop', 499.00, '2025-03-20', NULL, 0, 5, 1),
(7, b'1', 'Ta Xua is a stunning mountain destination in Northern Vietnam, famous for its breathtaking sea of clouds and winding scenic routes, making it a perfect spot for adventure seekers and nature lovers.', 'Ta Xua,VietNam', '2 days 1 night', '2025-04-23', '/images/1742398660871_taxua.jpg', 10, 'Chasing clouds in Ta Xua', 299.00, '2025-03-25', NULL, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `email`, `enabled`, `full_name`, `password`, `phone_number`, `role`, `username`) VALUES
(1, 'admin@traveltour.com', b'1', 'System Administrator', '$2a$10$lTkigHMmcZtx8lV/XmG2FOfLchCraMT.ymkHx0Q5Q/ZPSfY.A8M3W', NULL, 'ROLE_ADMIN', 'admin'),
(2, 'theboys2906@gmail.com', b'1', 'Huỳnh Anh Quốc', '$2a$10$pJxxvmKLsX7yZM.B02v4D.ivSB1n6gtcQLpeoet5ypWOKlSEdf8P.', '0388710603', 'ROLE_USER', 'quoc'),
(3, 'theboy@gmail.com', b'1', 'Trần Gia Huy', '$2a$10$z5oEe0AF.86ot6AXCqaY9OocqUMDfgZNMJnrnj4HRfNUoHwEqJBFy', '0338621525', 'ROLE_USER', 'Theboy'),
(4, 'ongn8434@gmail.com', b'1', 'Nguyễn Duy', '$2a$10$D9elGeGbHgbsJnlQoMtfoOUgYMt37a1goh6DY1MRwkY.tLEjNgWWW', '0338621525', 'ROLE_USER', 'Himmel');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKi21lisuytk5t7tlp7lv51ny2l` (`tour_id`),
  ADD KEY `FKeyog2oic85xg7hsu2je2lx3s6` (`user_id`);

--
-- Chỉ mục cho bảng `ratings`
--
ALTER TABLE `ratings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_5k3aadb6pujfi128ev8urriab` (`booking_id`),
  ADD KEY `FK3rsl9yok6g8pv26iqlu9nvoga` (`tour_id`),
  ADD KEY `FKb3354ee2xxvdrbyq9f42jdayd` (`user_id`);

--
-- Chỉ mục cho bảng `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg95fdc12cdl5o06q6la9jh0dm` (`tour_id`),
  ADD KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`);

--
-- Chỉ mục cho bảng `tours`
--
ALTER TABLE `tours`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `ratings`
--
ALTER TABLE `ratings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `tours`
--
ALTER TABLE `tours`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `FKeyog2oic85xg7hsu2je2lx3s6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKi21lisuytk5t7tlp7lv51ny2l` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`);

--
-- Các ràng buộc cho bảng `ratings`
--
ALTER TABLE `ratings`
  ADD CONSTRAINT `FK3rsl9yok6g8pv26iqlu9nvoga` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`),
  ADD CONSTRAINT `FKb3354ee2xxvdrbyq9f42jdayd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKkp31uofkhlk6pvyvmu3xcblkk` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`);

--
-- Các ràng buộc cho bảng `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKg95fdc12cdl5o06q6la9jh0dm` FOREIGN KEY (`tour_id`) REFERENCES `tours` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
