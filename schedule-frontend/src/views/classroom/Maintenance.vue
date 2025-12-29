<template>
	<!-- 搜索栏 -->
	<a-form layout="inline" style="margin-bottom: 40px">
		<a-form-item label="实验室">
			<a-select
				v-model:value="listParams.roomIds"
				mode="multiple"
				show-search
				placeholder="请选择实验室"
				style="width: 200px"
				:options="roomOptions"
				:filter-option="filterRoomOption"
			></a-select>
		</a-form-item>
		<a-button style="margin-right: 10px" @click="searchBtn">
			<template #icon>
				<search-outlined />
			</template>
			搜索
		</a-button>
		<a-button @click="resetBtn" style="margin-right: 10px" type="danger">
			<template #icon> <close-outlined /> </template>重置
		</a-button>
		<a-button v-permission="['sys:schedule:add']" style="margin-right: 10px" @click="addBtn" type="primary">
			<template #icon>
				<plus-outlined />
			</template>
			维护
		</a-button>
	</a-form>
	<FullCalendar ref="fullCalendar" class="demo-app-calendar" :options="calendarOptions">
		<template v-slot:eventContent="arg">
			<b>{{ arg.timeText }}</b>
			<i>{{ arg.event.title }}</i>
		</template>
	</FullCalendar>
	<!-- 约课界面 -->
	<add-maintenance ref="addRef" @refreshList="refreshList" />
	<!-- 编辑日历 -->
	<remove-maintenance ref="removeRef" @refreshList="refreshList" />
</template>
<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { EditType } from "@/type/BaseEnum";
import FullCalendar from "@fullcalendar/vue3";
import { CalendarOptions, EventClickArg } from "@fullcalendar/core";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import listPlugin from "@fullcalendar/list";
import type { SelectProps } from "ant-design-vue";
import tippy from "tippy.js";
import "tippy.js/dist/tippy.css";
import "tippy.js/themes/light.css";
import "tippy.js/animations/scale.css";
import AddMaintenance from "./AddMaintenance.vue";
import RemoveMaintenance from "./RemoveMaintenance.vue";
import interactionPlugin, { DateClickArg } from "@fullcalendar/interaction";

import { getRoomListApi as getClassrommList,} from "@/api/schedule/schedule";
import {  getClassroomReserveListApi } from "@/api/classroomReserve";
import { ClassroomReserveModel } from "@/api/classroomReserve/ClassroomReserveType";
import useRoomMaintenance from "@/composable/classroom/useMaintenance";

//教室列表数据
const roomOptions = ref<SelectProps["options"]>([]);
    const removeRef = ref();

const room = async () => {
    let res = await getClassrommList();
    if (res && res.code == 200) {
        roomOptions.value = res.data;
    }
};

const filterRoomOption = (input: string, option: any) => {
    return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
};


const listParams = reactive<Partial<ClassroomReserveModel>>({
    roomIds: [],
});


//日历的ref属性
const fullCalendar = ref();

const editClick = (clickInfo: EventClickArg) => {
    const obj = clickInfo.event.extendedProps;
	if (obj.type === 1) {
		//如果非实验室维护状态则不弹出详情页面
		return;
	}
    console.log(clickInfo.event.extendedProps);

    removeRef.value.show(
        clickInfo.event.extendedProps
    );
};
//上周
const prevWeekCustomClick = () => {
    fullCalendar.value.getApi().prev();
};
//下周
const nextWeekCustomClick = () => {
    fullCalendar.value.getApi().next();
};
//今天
const todayCustomClick = () => {
    fullCalendar.value.getApi().today();
    fullCalendar.value.getApi().getDate();
};
//上月
const prevMonthCustomClick = () => {
    fullCalendar.value.getApi().prev();
};
//下月
const nextMonthCustomClick = () => {
    fullCalendar.value.getApi().next();
};
//本月
const thisMonthCustomClick = () => {
    fullCalendar.value.getApi().today();
    fullCalendar.value.getApi().getDate();
};
//日历属性
const calendarOptions = reactive({
    plugins: [
        dayGridPlugin,
        listPlugin,
        timeGridPlugin,
        interactionPlugin, // needed for dateClick
    ],
    // 自定义按钮
    customButtons: {
        prevWeekCustom: {
            text: "上周",
            click: function () {
                prevWeekCustomClick();
            },
        },
        nextWeekCustom: {
            text: "下周",
            click: function () {
                nextWeekCustomClick();
            },
        },
        todayCustom: {
            text: "本周",
            click: function () {
                todayCustomClick();
            },
        },
        prevMonthCustom: {
            text: "上月",
            click: function () {
                prevMonthCustomClick();
            },
        },
        nextMonthCustom: {
            text: "下月",
            click: function () {
                nextMonthCustomClick();
            },
        },
        thisMonthCustom: {
            text: "本月",
            click: function () {
                thisMonthCustomClick();
            },
        },
    },
    headerToolbar: {
        start: "timeGridWeek,prevWeekCustom,todayCustom,nextWeekCustom",
        right: "dayGridMonth,prevMonthCustom,thisMonthCustom,nextMonthCustom",
        center: "title",
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
    initialView: "timeGridWeek",
    // 切换语言，当前为中文
    locale: "zh-cn",
    //月视图的显示模式，fixed：固定显示6周高；liquid：高度随周数变化；variable: 高度固定
    // weekMode: 'liquid',

    // 设置一周中显示的第一天是哪天，周日是0，周一是1，
    firstDay: 1,
    // 时间轴间距
    slotMinTime: "07:00",
    slotMaxTime: "21:00",
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
    //日历数据
    events: [],
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
    eventMouseEnter: function (info) {
        const content = info.event.extendedProps.type == 1 ?
        "<div style='width: 30rem; zIndex: 100'>" +
                "<div style='color: #666666'>实训项目: " +
                info.event.extendedProps.courseName +
                "</div>" +
                "<div style='color: #666666'>实验室: " +
                info.event.extendedProps.roomName +
                "</div>" +
                "<div style='color: #666666'>课时总数: " +
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
                "</div>"  +
                "</div>" +
                "</div>" :         "<div style='width: 15rem; zIndex: 100'>实验室: " + info.event.extendedProps.roomName + " 维护中</div>";
        tippy(info.el, {
            content,
            theme: "light",
            interactive: true,
            allowHTML: true,
            appendTo: document.body,
            zIndex: 9999999,
        });
    },

    //新增事件
    // dateClick: addClick,
    eventClick: editClick,
    // eventDrop: removeClick
} as CalendarOptions);


//获取日历数据
const getReservationList = async () => {
    const params = {
        type: 1
    } as ClassroomReserveModel;
    const res = await getClassroomReserveListApi(params);

    if (res && res.code == 200 && res.data.length > 0) {
        // console.log(res.data)
        //清空原来的数据
        calendarOptions.events = [];
        for (let i = 0; i < res.data.length; i++) {
            const data = res.data[i];
            //日历数据格式
            let obj = {
                id: data.id,
                title: data.type === 1 ? data.courseName : data.roomName + '正在维护中 ',
                start: data.dateTime + " " + data.beginTime,
                end: data.dateTime + " " + data.endTime,
                backgroundColor: data.type === 1 ? data.courseColor : '#000000',  //维护默认黑色
                className: "",
                //编辑的时候，可以从该属性获取数据
                extendedProps: data,
            };
            calendarOptions.events.push(obj);
        }
    }
};

//编辑后刷新
const refreshList = () => {
    getReservationList();
};

onMounted(() => {
    room();
    getReservationList();
});
defineExpose({
    getReservationList,
});

//教师的新增、编辑
const { addBtn, addRef, searchBtn,resetBtn } = useRoomMaintenance(listParams);
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
