<template>
	<!-- 搜索栏 -->
	<a-form layout="inline" style="margin-bottom: 40px;">
		<a-form-item label="实验室">
			<a-select
				v-model:value="listParams.roomIds"
				show-search
				mode="multiple"
				placeholder="请选择实验室"
				style="width: 200px"
				:options="roomOptions"
				:filter-option="filterRoomOption"
			></a-select>
		</a-form-item>
		<a-form-item label="实训项目">
			<a-select
				v-model:value="listParams.courseIds"
				show-search
				mode="multiple"
				placeholder="请选择实训项目"
				style="width: 200px"
				:options="courseOptions"
				:filter-option="filterCourseOption"
			></a-select>
		</a-form-item>
		<a-form-item label="教师">
			<a-select
				v-model:value="listParams.teacherIds"
				show-search
				mode="multiple"
				placeholder="请选择教师"
				style="width: 100px"
				:options="teacherOptions"
				:filter-option="filterTeacherOption"
			></a-select>
		</a-form-item>

		<a-button style="margin-right: 10px;" @click="searchBtn">
			<template #icon>
				<search-outlined />
			</template>
			搜索
		</a-button>
		<a-button @click="resetBtn" style="margin-right: 10px;" type="danger">
			<template #icon> <close-outlined /> </template>重置
		</a-button>
		<a-button v-permission="['sys:schedule:add']" style="margin-right: 10px;" @click="courseBtn" type="primary">
			<template #icon>
				<plus-outlined />
			</template>
			约课
		</a-button>
	</a-form>
	<FullCalendar ref="fullCalendar" class="demo-app-calendar" :options="calendarOptions">
		<template v-slot:eventContent="arg">
			<b>{{ arg.timeText }}</b>
			<i>{{ arg.event.title }}</i>
		</template>
	</FullCalendar>
	<!-- 约课界面 -->
	<add-schedule ref="addCourse" @refreshList="upSuccess"></add-schedule>
	<!-- 编辑日历 -->
	<CalendarEdit ref="editRef" @upSuccess="upSuccess"></CalendarEdit>
</template>
<script lang="ts" setup>
import type { SelectProps } from 'ant-design-vue';
import AddSchedule from './AddSchedule.vue';
import CalendarEdit from "./CalendarEdit.vue";
import { onMounted, reactive, ref } from "vue";
import { getAll } from "@/api/classroom/classroom";
import { EditType } from "@/type/BaseEnum";
import FullCalendar from "@fullcalendar/vue3";
import { CalendarOptions, EventClickArg } from "@fullcalendar/core";
import resourceTimelinePlugin from "@fullcalendar/resource-timeline";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import listPlugin from "@fullcalendar/list";
import tippy from "tippy.js";
import dayjs, { Dayjs } from "dayjs";
import "tippy.js/dist/tippy.css";
import "tippy.js/themes/light.css";
import "tippy.js/animations/scale.css";
import interactionPlugin, { DateClickArg } from "@fullcalendar/interaction";

import { getTeacherApi, getRoomListApi, getCourseListApi, getClassListApi } from '@/api/schedule/schedule';
import { ClassroomReserveModel } from "@/api/classroomReserve/ClassroomReserveType";
import {getClassroomReserveListApi} from '@/api/classroomReserve';

//日历的ref属性
const fullCalendar = ref();
//详情弹窗
const editRef = ref();
//约课弹窗
const addCourse = ref<{show:()=>void}>();

const listParams = reactive<ClassroomReserveModel>({
  roomIds: [],
  teacherIds: [],
  courseIds: [],
  dateTime: dayjs().format("YYYY-MM-DD"),
  type : 1  //检索全部
})
// 搜索
const searchBtn = () => {
    console.log(listParams);
    getScheduleList(listParams)
}

// 重置按钮
const resetBtn = () => {
    listParams.roomIds = [];
    listParams.courseIds = [];
    listParams.teacherIds = [];
    listParams.dateTime = dayjs().format("YYYY-MM-DD");
    getScheduleList(listParams)
}

//排课按钮
const courseBtn = ()=>{
    addCourse.value?.show()
}

//初始化页面
const roomOptions = ref<SelectProps['options']>([]);
//教师列表数据
const teacherOptions = ref<SelectProps['options']>([]);
//课程列表数据
const courseOptions = ref<SelectProps['options']>([]);
//班级列表数据
const classOptions = ref<SelectProps['options']>([]);

//获取教室列表
const teacher = async () => {
	let res = await getTeacherApi();
	if (res && res.code == 200) {
		teacherOptions.value = res.data;
	}
};
//获取教师列表
const room = async () => {
	let res = await getRoomListApi();
	if (res && res.code == 200) {
		roomOptions.value = res.data;
	}
};
//获取课程列表
const course = async () => {
	let res = await getCourseListApi();
	if (res && res.code == 200) {
		courseOptions.value = res.data;
	}
};
//获取班级列表
const clasz = async () => {
	let res = await getClassListApi();
	if (res && res.code == 200) {
		classOptions.value = res.data;
	}
};

const filterTeacherOption = (input: string, option: any) => {
	return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
};
const filterRoomOption = (input: string, option: any) => {
	return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
};
const filterCourseOption = (input: string, option: any) => {
	return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
};
const filterClassOption = (input: string, option: any) => {
	return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
};

//--------- 日历事件
//上一天
const prevDayCustomClick = () => {
    fullCalendar.value.getApi().prev();
    const prevDay = fullCalendar.value.getApi().getDate();
    const day = dayjs(prevDay).format("YYYY-MM-DD");
    getScheduleList({ dateTime: day });
};
//下一天
const nextDayCustomClick = () => {
    fullCalendar.value.getApi().next();
    const nextDay = fullCalendar.value.getApi().getDate();
    const day = dayjs(nextDay).format("YYYY-MM-DD");
    getScheduleList({ dateTime: day });
};
//今天
const todayCustomClick = () => {
    fullCalendar.value.getApi().today();
    fullCalendar.value.getApi().getDate();
};

//编辑事件
const editClick = (clickInfo: EventClickArg) => {
	const obj = clickInfo.event.extendedProps;
	if (obj.type === 0) {
		//如果实验室维护状态则不弹出详情页面
		return;
	}
	editRef.value.editCalender(EditType.EDIT, clickInfo.event.extendedProps);
};


//日历属性
const calendarOptions = reactive({
    schedulerLicenseKey: "CC-Attribution-NonCommercial-NoDerivatives",

    plugins: [
        dayGridPlugin,
        listPlugin,
        timeGridPlugin,
        interactionPlugin, // needed for dateClick
        resourceTimelinePlugin,
    ],
    // 自定义按钮
    customButtons: {
        prevDayCustom: {
            text: "上一天",
            click: function () {
                prevDayCustomClick();
            },
        },
        todayCustom: {
            text: "下一天",
            click: function () {
                nextDayCustomClick();
            },
        },
        nextDayCustom: {
            text: "今天",
            click: function () {
                todayCustomClick();
            },
        },
    },
    headerToolbar: {
        left: "prevDayCustom,todayCustom,nextDayCustom",
        center: "title",
        right: "",
    },
    //修改headerToolbar的文字
    buttonText: {
        today: "今天",
        month: "月视图",
        week: "周视图",
        day: "日",
        list: "周列表",
    },

    //设置日历的高度
    contentHeight: window.innerHeight - 260,
    // 默认为那个视图（月：dayGridMonth，周：timeGridWeek，日：timeGridDay）
    initialView: "resourceTimeline",
    // 切换语言，当前为中文
    locale: "zh-cn",
    //月视图的显示模式，fixed：固定显示6周高；liquid：高度随周数变化；variable: 高度固定
    // weekMode: 'liquid',

    // 设置一周中显示的第一天是哪天，周日是0，周一是1，
    firstDay: 1,
    // 时间轴间距
    slotMinTime: "07:00",
    slotMaxTime: "23:00",
    slotDuration: "00:" + 15,
    slotLabelFormat: {
        hour: "numeric",
        minute: "2-digit",
        hour12: false,
    },
    eventTimeFormat: {
        hour: "numeric",
        minute: "2-digit",
        hour12: false,
    },
    resourceOrder: '-orderNum',
    resourceAreaHeaderContent: "实验室",
    //Event是否可以进行（拖动、缩放）修改
    editable: false,
    // 是否显示全天插槽
    allDaySlot: false,
    // 是否显示当前时间标记
    nowIndicator: true,
    //是否随浏览器窗口大小变化而自动变化
    handleWindowResize: true,
    // 日期否可点击
    navLinks: false,
    // 月视图，是否为指定周数高度
    fixedWeekCount: true,
    // 月视图，是否显示非本月日期
    showNonCurrentDates: false,
    //设置是否可被单击选中
    selectable: false,
    selectMirror: false,
    dayMaxEvents: false,
    weekends: true,
    eventMouseEnter: function (info: any) {
        console.log(info.event.extendedProps.roomName);
        tippy(info.el, {
            content:
                "<div style='width: 30rem; zIndex: 100'>" +
                "<div style='color: #666666'>实训项目: " +
                info.event.extendedProps.courseName +
                "</div>" +
                "<div style='color: #666666'>实验室: " +
                info.event.extendedProps.roomName +
                "</div>" +
                "<div style='color: #666666'>课时总数: " +
                    "共" +
                info.event.extendedProps.sequenceTotal +
                "节" +
                "</div>" +
                "<div style='color: #666666'>班级: " +
                info.event.extendedProps.className +
                "</div>" +
                "<div style='color: #666666'>人数: " +
                info.event.extendedProps.classNumber +
                "</div>" +
                "<div style='color: #666666'>老师: " +
                info.event.extendedProps.teacherName +
                "</div>",
            theme: "light",
            interactive: true,
            allowHTML: true,
            appendTo: document.body,
            zIndex: 9999999,
        });
    },

    resources: async () => {
        const classroom = [];
        const res = await getAll();
        console.log(res);
        if (res && res.code == 200 && res.data.length > 0) {
            for (let i = 0; i < res.data.length; i++) {
                classroom.push({
                    id: res.data[i].roomId,
                    title: res.data[i].roomName,
                });
            }
            return classroom;
        }
    },

    //新增事件
    // dateClick: addClick,
    eventClick: editClick,
    // eventDrop: removeClick
} as CalendarOptions);


//获取日历数据
const getScheduleList = async (params: ClassroomReserveModel) => {
    console.log(params);
    params = {
        type : 1,    //查询所有类型
        ...params
    }
    const res = await getClassroomReserveListApi(params);
    if (res && res.code == 200 && res.data.length > 0) {
        console.log(res.data);
        //清空原来的数据
        calendarOptions.events = [];
        for (let i = 0; i < res.data.length; i++) {
            //日历数据格式
            let obj = {
                resourceId: "",
                id: "",
                title: "",
                start: "",
                end: "",
                backgroundColor: "",
                className: "",
                //编辑的时候，可以从该属性获取数据
                extendedProps: Object,
            };
            const data = res.data[i];
            (obj.id = data.id),
            (obj.resourceId = data.roomId),
            (obj.title = data.type === 1 ? data.courseName + " " + data.roomName : '维护中 ' + ' ' + data.roomName),
            (obj.start = data.dateTime + " " + data.beginTime),
            (obj.end = data.dateTime + " " + data.endTime),
            (obj.backgroundColor = data.type === 1 ? data.courseColor : '#000000'),
            (obj.extendedProps = data);
            calendarOptions.events.push(obj);
        }
    }
    //console.log(calendarOptions);
};
//新增、编辑成功刷新
const upSuccess = () => {
    const day = dayjs().format("YYYY-MM-DD");
    getScheduleList({ dateTime: day });
};

onMounted(() => {
    const day = dayjs().format("YYYY-MM-DD");
    getScheduleList({ dateTime: day });
    teacher();
	course();
	room();
	clasz();
});

defineExpose({
    getScheduleList,
});
</script>

<style scoped lang="scss">
:deep(.fc-button-primary) {
    background-color: #1890ff !important;
    border-color: #fff !important;
}
:deep(.bgs1) {
    background: #ff7670 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs2) {
    background: #27ae60 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs3) {
    background: #1890ff !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs4) {
    background: #009688 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs5) {
    background: #5fb878 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs6) {
    background: #ff5722 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs7) {
    background: #7705f9 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs8) {
    background: #009688 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs9) {
    background: #4c03f4 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs10) {
    background: #f22d6f !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs11) {
    background: #f73838 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs12) {
    background: #3874f7 !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs13) {
    background: #e066ff !important;
    border-color: #fff !important;
    color: #fff !important;
}
:deep(.bgs14) {
    background: #ffb800 !important;
    border-color: #fff !important;
    color: #fff !important;
}
</style>
