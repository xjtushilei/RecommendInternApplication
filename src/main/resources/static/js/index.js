var table = layui.table;
var tableIns;
var vm = new Vue({
    el: "#main",
    data: {
        questions: [],
        finalDisease: [],
        recommendation: "",
        notes: [],
        sessionId: "",
        note: "",
        name: "输入名字",
        tableobj: {},
        notesum: 10
    },
    methods: {
        init: function () {
            //执行渲染
            tableIns = table.render({
                elem: '#table' //指定原始表格元素选择器（推荐id选择器）
                , cols: [[ //标题栏
                    {field: 'sessionId', title: 'id'}
                    , {field: 'time', title: '时间'}
                    , {field: 'status', title: '最后状态'}
                    // , {field: 'name', title: '姓名'}
                    , {field: 'sex', title: '性别'}
                    , {field: 'dob', title: '出生日期'}
                    , {field: 'cardNo', title: 'cardNo'}
                    // , {field: 'tishi', title: '问答详情', event: 'questions', style: 'cursor: pointer;'}
                    , {field: 'tishi', title: '问答及其结果', event: 'recommendation', style: 'cursor: pointer;'}
                    , {field: 'tishi', title: '评论', event: 'notes', style: 'cursor: pointer;'}
                    , {field: 'noteSum', title: '评论数量'}
                ]]
                , cellMinWidth: 60 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
                , url: '/api/log/getlog'
                , where: {debug: 0} //如果无需传递额外参数，可不加该参数
                , method: 'get'
                , limit: 25
                , limits: [10, 20, 25, 30, 40, 50, 60, 70, 80, 90]
                , even: true //开启隔行背景
                , page: true
                , skin: 'nob' //行边框风格
                , size: 'sm' //小尺寸的表格

            });

            //监听单元格事件
            table.on('tool(demoEvent)', function (obj) {
                tableobj = obj
                vm.notesum = obj.data.noteSum
                var data = obj.data;
                console.log(data)
                if (obj.event === 'recommendation') {
                    vm.questions = JSON.parse(data.questions)
                    vm.finalDisease = JSON.parse(data.finalDisease)
                    console.log(vm.finalDisease)
                    $('#recommendation').modal('toggle')
                    vm.recommendation = JSON.parse(data.recommendation)
                    console.log(vm.recommendation)
                } else if (obj.event === 'notes') {
                    vm.sessionId = data.sessionId
                    vm.getnotes()
                }
            });
        } //end init
        , addnote: function () {
            $.ajax({
                type: "post",
                url: "api/log/addnote",
                data: {sessionId: vm.sessionId, note: vm.note, name: vm.name},
                dataType: "text",
                success: function (data) {
                    console.log(data)
                    vm.getnotes()
                    vm.note = ""
                    vm.notesum = vm.notesum + 1
                    tableobj.update(
                        {
                            noteSum: vm.notesum
                        }
                    )
                }
            });
        }
        , deletenote: function (id) {
            if (confirm("确认删除？")) {
                $.ajax({
                    type: 'DELETE',
                    url: "api/log/deletenote?id=" + id,
                    dataType: "text",
                    success: function (data) {
                        console.log(data)
                        vm.getnotes()
                        vm.notesum = vm.notesum - 1
                        tableobj.update(
                            {
                                noteSum: vm.notesum
                            }
                        )
                        alert(data)
                    }
                });
            }
        }
        , getnotes: function () {
            $.ajax({
                type: 'get',
                url: "api/log/getnotes?sessionId=" + vm.sessionId,
                dataType: "json",
                success: function (data) {
                    vm.notes = data
                    $('#notes').modal('show')
                }
            });
        }


    }
})
vm.init()







