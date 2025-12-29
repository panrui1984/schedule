/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : schedule

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 02/11/2023 22:57:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom`  (
  `room_id` int NOT NULL AUTO_INCREMENT COMMENT '教室id',
  `room_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教室名称',
  `room_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教室地址',
  PRIMARY KEY (`room_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES (1, '101', '教室101');
INSERT INTO `classroom` VALUES (2, '102', '教室102');
INSERT INTO `classroom` VALUES (3, '103', '教室103');

-- ----------------------------
-- Table structure for clsases
-- ----------------------------
DROP TABLE IF EXISTS `clsases`;
CREATE TABLE `clsases`  (
  `class_id` int NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `class_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '名称',
  `calass_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of clsases
-- ----------------------------
INSERT INTO `clsases` VALUES (1, '班级1', '班级1');
INSERT INTO `clsases` VALUES (2, '班级2', '班级1');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` int NOT NULL AUTO_INCREMENT COMMENT '课程id',
  `course_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '课程名称',
  `course_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '课程类型',
  `course_year` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '课程年份',
  `course_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `course_color` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '课程颜色',
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'java基础', '0', '2023', '2022-02-21 17:01:05', 'bgs1-#FF7670');
INSERT INTO `course` VALUES (2, 'c语言基础', '0', '2022', '2022-02-26 10:42:19', 'bgs2-#27ae60');
INSERT INTO `course` VALUES (7, '高等数学', '0', '2022', '2022-02-26 10:43:46', 'bgs5-#1890ff');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` int NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` int NULL DEFAULT NULL COMMENT '父级菜单id',
  `title` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限字段',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由名称',
  `path` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由path',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组件路径',
  `type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '类型(0 目录 1菜单，2按钮)',
  `icon` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图标',
  `parent_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上级菜单名称',
  `order_num` int NULL DEFAULT NULL COMMENT '序号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (3, 0, '系统管理', 'sys:system', 'system', '/system', '', '0', 'menu-outlined', '顶级菜单', 1, NULL, NULL);
INSERT INTO `menu` VALUES (4, 3, '用户管理', 'sys:user', 'user', '/user', '/system/user/User.vue', '1', 'user-outlined', '系统管理', 2, NULL, '2022-03-09 13:19:10');
INSERT INTO `menu` VALUES (5, 3, '角色管理', 'sys:role', 'role', '/role', '/system/role/Role.vue', '1', 'contacts-outlined', '系统管理', 3, NULL, '2022-03-05 09:18:00');
INSERT INTO `menu` VALUES (6, 3, '菜单管理', 'sys:menu', 'menu', '/menu', '/system/menu/Menu.vue', '1', 'hdd-outlined', '系统管理', 4, '2022-03-04 19:11:39', '2022-03-04 19:11:39');
INSERT INTO `menu` VALUES (7, 0, '课程管理', 'sys:courseRoot', 'courseRoot', '/courseRoot', '', '0', 'border-outer-outlined', '顶级菜单', 5, '2022-03-04 19:14:57', '2022-03-04 19:14:57');
INSERT INTO `menu` VALUES (8, 7, '课程列表', 'sys:course', 'course', '/course', '/course/Course.vue', '1', 'pic-center-outlined', '课程管理', 6, '2022-03-04 19:16:26', '2022-03-04 19:16:26');
INSERT INTO `menu` VALUES (10, 0, '教师管理', 'sys:teacher', 'teacherRoot', '/teacherRoot', '', '0', 'align-center-outlined', '顶级菜单', 8, '2022-03-05 08:52:44', '2022-03-05 08:52:44');
INSERT INTO `menu` VALUES (11, 10, '教师列表', 'sys:teacher:list', 'teacher', '/teacher', '/teacher/Teacher.vue', '1', 'pic-left-outlined', '教师管理', 8, '2022-03-05 08:58:15', '2022-03-05 08:58:15');
INSERT INTO `menu` VALUES (15, 0, '教室管理', 'sys:room', 'classroomRoot', '/classroomRoot', '', '0', 'bank-outlined', '顶级菜单', 9, '2022-03-05 11:44:26', '2022-03-05 11:44:26');
INSERT INTO `menu` VALUES (16, 15, '教室列表', 'sys:classroom', 'classroom', '/classroom', '/classroom/Classroom.vue', '1', 'bars-outlined', '教室管理', 12, '2022-03-05 11:55:29', '2022-03-05 11:55:29');
INSERT INTO `menu` VALUES (17, 0, '排课管理', 'sys:scheduleRoot', 'scheduleRoot', '/scheduleRoot', '', '0', 'contacts-outlined', '顶级菜单', 13, '2022-03-05 11:57:11', '2022-03-05 11:57:11');
INSERT INTO `menu` VALUES (18, 17, '排课日历', 'sys:schedule', 'schedule', '/schedule', '/schedule/Schedule.vue', '1', 'credit-card-outlined', '排课管理', 14, '2022-03-05 11:58:38', '2022-03-05 11:58:38');
INSERT INTO `menu` VALUES (19, 17, '排课列表', 'sys:scheduleList', 'scheduleList', '/scheduleList', '/schedule/ScheduleList.vue', '1', 'file-done-outlined', '排课管理', 15, '2022-03-05 11:59:53', '2022-03-05 11:59:53');
INSERT INTO `menu` VALUES (20, 4, '新增', 'sys:user:add', '', '', '', '2', '', '用户管理', 1, '2022-03-05 12:02:35', '2022-03-05 12:02:35');
INSERT INTO `menu` VALUES (21, 4, '编辑', 'sys:user:edit', '', '', '', '2', '', '用户管理', 2, '2022-03-05 12:03:02', '2022-03-10 15:11:50');
INSERT INTO `menu` VALUES (22, 4, '删除', 'sys:user:delete', '', '', '', '2', '', '用户管理', 3, '2022-03-05 12:05:15', '2022-03-05 12:05:15');
INSERT INTO `menu` VALUES (23, 5, '新增', 'sys:role:add', '', '', '', '2', '', '角色管理', 1, '2022-03-05 12:05:38', '2022-03-05 12:05:38');
INSERT INTO `menu` VALUES (24, 5, '编辑', 'sys:role:edit', '', '', '', '2', '', '角色管理', 2, '2022-03-05 12:06:02', '2022-03-05 12:06:02');
INSERT INTO `menu` VALUES (25, 5, '删除', 'sys:role:delete', '', '', '', '2', '', '角色管理', 3, '2022-03-05 12:06:23', '2022-03-05 12:06:23');
INSERT INTO `menu` VALUES (26, 6, '新增', 'sys:menu:add', '', '', '', '2', '', '菜单管理', 1, '2022-03-05 12:06:46', '2022-03-05 12:06:46');
INSERT INTO `menu` VALUES (27, 6, '编辑', 'sys:menu:edit', '', '', '', '2', '', '菜单管理', 2, '2022-03-05 12:07:11', '2022-03-05 12:07:11');
INSERT INTO `menu` VALUES (28, 6, '删除', 'sys:menu:detete', '', '', '', '2', '', '菜单管理', 3, '2022-03-05 12:07:35', '2022-03-05 12:07:35');
INSERT INTO `menu` VALUES (29, 8, '新增', 'sys:course:add', '', '', '', '2', '', '课程列表', 1, '2022-03-05 12:08:00', '2022-03-05 12:08:00');
INSERT INTO `menu` VALUES (30, 8, '编辑', 'sys:course:edit', '', '', '', '2', '', '课程列表', 2, '2022-03-05 12:08:21', '2022-03-05 12:08:21');
INSERT INTO `menu` VALUES (31, 8, '删除', 'sys:course:detete', '', '', '', '2', '', '课程列表', 3, '2022-03-05 12:08:43', '2022-03-05 12:08:43');
INSERT INTO `menu` VALUES (32, 11, '新增', 'sys:teacher:add', '', '', '', '2', '', '教师列表', 1, '2022-03-05 12:09:11', '2022-03-05 12:09:11');
INSERT INTO `menu` VALUES (33, 11, '编辑', 'sys:teacher:edit', '', '', '', '2', '', '教师列表', 2, '2022-03-05 12:09:26', '2022-03-05 12:09:26');
INSERT INTO `menu` VALUES (34, 11, '删除', 'sys:teacher:delete', '', '', '', '2', '', '教师列表', 3, '2022-03-05 12:09:46', '2022-03-05 12:09:46');
INSERT INTO `menu` VALUES (35, 16, '新增', 'sys:room:add', '', '', '', '2', '', '教室列表', 1, '2022-03-05 12:10:18', '2022-03-05 12:10:18');
INSERT INTO `menu` VALUES (36, 16, '编辑', 'sys:room:edit', '', '', '', '2', '', '教室列表', 2, '2022-03-05 12:10:35', '2022-03-05 12:10:35');
INSERT INTO `menu` VALUES (37, 16, '删除', 'sys:room:delete', '', '', '', '2', '', '教室列表', 3, '2022-03-05 12:10:56', '2022-03-05 12:10:56');
INSERT INTO `menu` VALUES (38, 18, '排课', 'sys:schedule:add', '', '', '', '2', '', '排课日历', 1, '2022-03-05 12:11:32', '2022-03-05 12:11:32');
INSERT INTO `menu` VALUES (39, 18, '编辑', 'sys:schedule:edit', '', '', '', '2', '', '排课日历', 2, '2022-03-05 12:11:59', '2022-03-05 12:11:59');
INSERT INTO `menu` VALUES (40, 18, '移动', 'sys:schedule:remove', '', '', '', '2', '', '排课日历', 3, '2022-03-05 12:12:21', '2022-03-05 12:12:21');
INSERT INTO `menu` VALUES (41, 19, '导出', 'sys:scheduleList:export', '', '', '', '2', '', '排课列表', 1, '2022-03-05 12:12:54', '2022-03-05 12:12:54');
INSERT INTO `menu` VALUES (42, 5, '分配权限', 'sys:role:permission', '', '', '', '2', '', '角色管理', NULL, '2022-03-10 15:05:58', '2022-03-10 15:05:58');
INSERT INTO `menu` VALUES (48, 0, '班级管理', 'sys:classesRoot', 'classesRoot', '/classesRoot', '', '0', 'contacts-outlined', '顶级菜单', 4, '2022-05-06 11:03:09', '2022-05-06 11:03:26');
INSERT INTO `menu` VALUES (49, 48, '班级列表', 'sys:classes:list', 'classesList', '/classesList', '/classes/list.vue', '1', 'pic-center-outlined', '班级管理', 1, '2022-05-06 11:04:31', '2022-05-06 11:04:47');
INSERT INTO `menu` VALUES (50, 49, '新增', 'sys:classes:add', '', '', '', '2', '', '班级列表', 1, '2022-05-06 11:05:12', '2022-05-06 11:05:12');
INSERT INTO `menu` VALUES (51, 49, '编辑', 'sys:classes:edit', '', '', '', '2', '', '班级列表', 2, '2022-05-06 11:05:32', '2022-05-06 11:05:32');
INSERT INTO `menu` VALUES (52, 49, '删除', 'sys:classes:delete', '', '', '', '2', '', '班级列表', 3, '2022-05-06 11:05:49', '2022-05-06 11:05:49');
INSERT INTO `menu` VALUES (54, 53, '新增', 'sys:student:add', '', '', '', '2', '', '学生管理', 1, '2022-05-06 14:09:07', '2022-05-06 14:09:07');
INSERT INTO `menu` VALUES (55, 53, '编辑', 'sys:student:edit', '', '', '', '2', '', '学生管理', 2, '2022-05-06 14:09:34', '2022-05-06 14:09:34');
INSERT INTO `menu` VALUES (56, 53, '删除', 'sys:student:delete', '', '', '', '2', '', '学生管理', 3, '2022-05-06 14:09:54', '2022-05-06 14:09:54');
INSERT INTO `menu` VALUES (57, 59, '选课', 'sys:course:select', '', '', '', '2', '', '在线选课', 3, '2022-05-09 15:34:49', '2022-05-09 20:34:57');
INSERT INTO `menu` VALUES (58, 60, '退课', 'sys:course:return', '', '', '', '2', '', '我的课程', 5, '2022-05-09 17:39:28', '2022-05-09 20:36:53');
INSERT INTO `menu` VALUES (59, 7, '在线选课', 'sys:course:onlineselect', 'selectcourse', '/selectcourse', '/course/selectcourse.vue', '1', 'bank-outlined', '课程管理', NULL, '2022-05-09 18:27:06', '2022-05-09 18:29:02');
INSERT INTO `menu` VALUES (60, 7, '我的课程', 'sys:course:mycourse', 'mycourse', '/mycourse', '/course/studentcourse.vue', '1', 'pic-center-outlined', '课程管理', 3, '2022-05-09 20:36:19', '2022-05-09 20:36:40');
INSERT INTO `menu` VALUES (61, 7, '我的授课', 'sys:course:myteacher', 'myteacher', '/myteacher', '/course/teachercourse.vue', '1', 'contacts-outlined', '课程管理', NULL, '2022-05-10 09:10:17', '2022-05-10 09:10:38');
INSERT INTO `menu` VALUES (62, 61, '导出课表', 'sys:myteacher:export', '', '', '', '2', '', '我的授课', NULL, '2022-05-10 09:11:07', '2022-05-10 09:11:07');
INSERT INTO `menu` VALUES (63, 11, '重置密码', 'sys:teacher:reset', '', '', '', '2', '', '教师列表', NULL, '2022-05-10 11:23:36', '2022-05-10 11:23:36');
INSERT INTO `menu` VALUES (64, 53, '重置密码', 'sys:student:reset', '', '', '', '2', '', '学生管理', NULL, '2022-05-10 11:24:44', '2022-05-10 11:24:44');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色名称',
  `role_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '超级管理员');
INSERT INTO `role` VALUES (3, '测试', '测试');
INSERT INTO `role` VALUES (4, '系统管理员', '系统管理员');
INSERT INTO `role` VALUES (5, '教师角色', '教师角色');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_menu_id` int NOT NULL AUTO_INCREMENT COMMENT '角色菜单id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 579 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (64, 3, 20);
INSERT INTO `role_menu` VALUES (65, 3, 21);
INSERT INTO `role_menu` VALUES (66, 3, 22);
INSERT INTO `role_menu` VALUES (67, 3, 4);
INSERT INTO `role_menu` VALUES (68, 3, 15);
INSERT INTO `role_menu` VALUES (69, 3, 16);
INSERT INTO `role_menu` VALUES (70, 3, 35);
INSERT INTO `role_menu` VALUES (71, 3, 36);
INSERT INTO `role_menu` VALUES (72, 3, 37);
INSERT INTO `role_menu` VALUES (73, 3, 3);
INSERT INTO `role_menu` VALUES (437, 6, 57);
INSERT INTO `role_menu` VALUES (438, 6, 58);
INSERT INTO `role_menu` VALUES (439, 6, 59);
INSERT INTO `role_menu` VALUES (440, 6, 60);
INSERT INTO `role_menu` VALUES (441, 6, 7);
INSERT INTO `role_menu` VALUES (489, 1, 42);
INSERT INTO `role_menu` VALUES (490, 1, 63);
INSERT INTO `role_menu` VALUES (491, 1, 35);
INSERT INTO `role_menu` VALUES (492, 1, 50);
INSERT INTO `role_menu` VALUES (493, 1, 38);
INSERT INTO `role_menu` VALUES (494, 1, 32);
INSERT INTO `role_menu` VALUES (495, 1, 23);
INSERT INTO `role_menu` VALUES (496, 1, 20);
INSERT INTO `role_menu` VALUES (497, 1, 26);
INSERT INTO `role_menu` VALUES (498, 1, 54);
INSERT INTO `role_menu` VALUES (499, 1, 29);
INSERT INTO `role_menu` VALUES (500, 1, 55);
INSERT INTO `role_menu` VALUES (501, 1, 33);
INSERT INTO `role_menu` VALUES (502, 1, 39);
INSERT INTO `role_menu` VALUES (503, 1, 24);
INSERT INTO `role_menu` VALUES (504, 1, 21);
INSERT INTO `role_menu` VALUES (505, 1, 27);
INSERT INTO `role_menu` VALUES (506, 1, 36);
INSERT INTO `role_menu` VALUES (507, 1, 30);
INSERT INTO `role_menu` VALUES (508, 1, 51);
INSERT INTO `role_menu` VALUES (509, 1, 52);
INSERT INTO `role_menu` VALUES (510, 1, 28);
INSERT INTO `role_menu` VALUES (511, 1, 22);
INSERT INTO `role_menu` VALUES (512, 1, 25);
INSERT INTO `role_menu` VALUES (513, 1, 34);
INSERT INTO `role_menu` VALUES (514, 1, 37);
INSERT INTO `role_menu` VALUES (515, 1, 31);
INSERT INTO `role_menu` VALUES (516, 1, 56);
INSERT INTO `role_menu` VALUES (517, 1, 40);
INSERT INTO `role_menu` VALUES (518, 1, 4);
INSERT INTO `role_menu` VALUES (519, 1, 5);
INSERT INTO `role_menu` VALUES (520, 1, 6);
INSERT INTO `role_menu` VALUES (521, 1, 8);
INSERT INTO `role_menu` VALUES (522, 1, 11);
INSERT INTO `role_menu` VALUES (523, 1, 16);
INSERT INTO `role_menu` VALUES (524, 1, 18);
INSERT INTO `role_menu` VALUES (525, 1, 49);
INSERT INTO `role_menu` VALUES (526, 1, 3);
INSERT INTO `role_menu` VALUES (527, 1, 10);
INSERT INTO `role_menu` VALUES (528, 1, 15);
INSERT INTO `role_menu` VALUES (529, 1, 64);
INSERT INTO `role_menu` VALUES (531, 1, 48);
INSERT INTO `role_menu` VALUES (532, 1, 7);
INSERT INTO `role_menu` VALUES (533, 1, 17);
INSERT INTO `role_menu` VALUES (567, 5, 62);
INSERT INTO `role_menu` VALUES (568, 5, 38);
INSERT INTO `role_menu` VALUES (569, 5, 41);
INSERT INTO `role_menu` VALUES (570, 5, 39);
INSERT INTO `role_menu` VALUES (571, 5, 40);
INSERT INTO `role_menu` VALUES (572, 5, 57);
INSERT INTO `role_menu` VALUES (573, 5, 18);
INSERT INTO `role_menu` VALUES (574, 5, 19);
INSERT INTO `role_menu` VALUES (575, 5, 59);
INSERT INTO `role_menu` VALUES (576, 5, 61);
INSERT INTO `role_menu` VALUES (577, 5, 17);
INSERT INTO `role_menu` VALUES (578, 5, 7);

-- ----------------------------
-- Table structure for schedule_course
-- ----------------------------
DROP TABLE IF EXISTS `schedule_course`;
CREATE TABLE `schedule_course`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_id` int NULL DEFAULT NULL COMMENT '课程id',
  `teacher_id` int NULL DEFAULT NULL COMMENT '教师id',
  `room_id` int NULL DEFAULT NULL COMMENT '教室id',
  `date_time` date NULL DEFAULT NULL COMMENT '日期',
  `sequence` int NULL DEFAULT NULL COMMENT '课程序号（1-12）',
  `begin_time` time NULL DEFAULT NULL COMMENT '上课时间',
  `end_time` time NULL DEFAULT NULL COMMENT '下课时间',
  `duration` int NULL DEFAULT NULL COMMENT '课程时长',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '使用目的',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of schedule_course
-- ----------------------------
INSERT INTO `schedule_course` VALUES (2, 1, 12, 1, '2023-11-10', NULL, '08:00:00', '08:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (3, 1, 12, 1, '2023-11-17', NULL, '08:00:00', '08:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (5, 1, 12, 1, '2023-12-01', NULL, '08:00:00', '08:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (7, 2, 13, 2, '2023-11-10', NULL, '08:00:00', '08:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (8, 2, 13, 2, '2023-11-17', NULL, '08:00:00', '08:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (10, 7, 13, 2, '2023-11-06', NULL, '09:00:00', '09:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (11, 7, 13, 2, '2023-11-13', NULL, '09:00:00', '09:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (13, 7, 13, 2, '2023-11-27', NULL, '09:00:00', '09:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (14, 7, 13, 2, '2023-12-04', NULL, '09:00:00', '09:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (15, 7, 13, 2, '2023-12-11', NULL, '09:00:00', '09:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (16, 7, 13, 2, '2023-12-18', NULL, '09:00:00', '09:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (17, 7, 13, 2, '2023-12-25', NULL, '09:00:00', '09:45:00', 45, NULL, NULL);
INSERT INTO `schedule_course` VALUES (18, 1, 1, 1, '2023-11-06', NULL, '08:00:00', '08:45:00', 45, '测试目的', '');

-- ----------------------------
-- Table structure for stu_role
-- ----------------------------
DROP TABLE IF EXISTS `stu_role`;
CREATE TABLE `stu_role`  (
  `stu_role_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stu_id` int NULL DEFAULT NULL COMMENT '学生id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`stu_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of stu_role
-- ----------------------------
INSERT INTO `stu_role` VALUES (1, 1, 6);
INSERT INTO `stu_role` VALUES (2, 3, 6);
INSERT INTO `stu_role` VALUES (3, 4, 6);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` int NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `class_id` int NULL DEFAULT NULL COMMENT '班级id',
  `stu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学生姓名',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话',
  `stu_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '学号',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 1, '11', '0', '111', '11', 'f379eaf3c831b04de153469d1bec345e');
INSERT INTO `student` VALUES (3, 2, '2266', '0', '2266', '2266', 'f379eaf3c831b04de153469d1bec345e');
INSERT INTO `student` VALUES (4, 1, '22', '0', '22', '222', 'bcbe3365e6ac95ea2c0343a2395834dd');

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course`  (
  `stu_course_id` int NOT NULL AUTO_INCREMENT,
  `stu_id` int NULL DEFAULT NULL,
  `course_id` int NULL DEFAULT NULL,
  `teacher_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`stu_course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student_course
-- ----------------------------
INSERT INTO `student_course` VALUES (5, 1, 1, 1);
INSERT INTO `student_course` VALUES (7, 1, 6, 2);
INSERT INTO `student_course` VALUES (9, 3, 1, 1);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `teacher_id` int NOT NULL AUTO_INCREMENT COMMENT '教师id',
  `teacher_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教师姓名',
  `teacher_num` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教师编号',
  `teacher_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教师描述',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '张三', '2022001', '英语老师', 'f379eaf3c831b04de153469d1bec345e');
INSERT INTO `teacher` VALUES (2, '李四', '2022002', 'java老师', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `teacher` VALUES (12, '老师1', 'teacher1', '新增老师1', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `teacher` VALUES (13, '潘瑞2', 'teacher05', 'a', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `teacher` VALUES (14, '测试老师1', '0000152646', '', 'e10adc3949ba59abbe56e057f20f883e');

-- ----------------------------
-- Table structure for teacher_role
-- ----------------------------
DROP TABLE IF EXISTS `teacher_role`;
CREATE TABLE `teacher_role`  (
  `teacher_id` int NULL DEFAULT NULL COMMENT '教师id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `teacher_role_id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  PRIMARY KEY (`teacher_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teacher_role
-- ----------------------------
INSERT INTO `teacher_role` VALUES (2, 5, 9);
INSERT INTO `teacher_role` VALUES (1, 5, 12);
INSERT INTO `teacher_role` VALUES (9, 5, 13);
INSERT INTO `teacher_role` VALUES (10, 5, 14);
INSERT INTO `teacher_role` VALUES (11, NULL, 15);
INSERT INTO `teacher_role` VALUES (12, 5, 17);
INSERT INTO `teacher_role` VALUES (13, 5, 18);
INSERT INTO `teacher_role` VALUES (14, 5, 20);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录账户',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别 0：男 1：女',
  `is_admin` tinyint NULL DEFAULT NULL COMMENT '超级管理员 1：是 0：否',
  `is_account_non_expired` tinyint NULL DEFAULT NULL COMMENT '帐户是否过期(1 未过期，0已过期)',
  `is_account_non_locked` tinyint NULL DEFAULT NULL COMMENT '帐户是否被锁定(1 未锁定，0已锁定)',
  `is_credentials_non_expired` tinyint NULL DEFAULT NULL COMMENT '密码是否过期(1 未过期，0已过期)',
  `is_enabled` tinyint NULL DEFAULT NULL COMMENT '帐户是否可用(1 可用，0 删除用户)',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '18687116223', '3501754007@qqcom', '0', 1, 1, 1, 1, 1, '张三', NULL, NULL);
INSERT INTO `user` VALUES (3, 'test', 'e10adc3949ba59abbe56e057f20f883e', '187871706', '', '1', NULL, 1, 1, 1, 1, '李四', NULL, NULL);
INSERT INTO `user` VALUES (4, 'test1', 'e10adc3949ba59abbe56e057f20f883e', '186871654613', '', '0', NULL, 1, 1, 1, 1, '王五', NULL, NULL);
INSERT INTO `user` VALUES (5, 'teacher1', 'e10adc3949ba59abbe56e057f20f883e', '18914766696', '', '0', NULL, 1, 1, 1, 1, '老师1', NULL, NULL);
INSERT INTO `user` VALUES (6, 'teacher03', 'e10adc3949ba59abbe56e057f20f883e', '1111', '', '0', NULL, 1, 1, 1, 1, '老师3', NULL, NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_role_id` int NOT NULL AUTO_INCREMENT COMMENT '用户角色id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 1);
INSERT INTO `user_role` VALUES (3, 3, 4);
INSERT INTO `user_role` VALUES (4, 4, 3);
INSERT INTO `user_role` VALUES (5, 5, 5);
INSERT INTO `user_role` VALUES (6, 6, 5);

SET FOREIGN_KEY_CHECKS = 1;
