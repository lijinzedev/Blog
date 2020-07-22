function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    $.ajax(
        {
            type:"POST",
            contentType:'application/json;charset=UTF-8',
            url:"/comment",
            data: JSON.stringify({
                "parentId":questionId,
                "content":commentContent,
                "type":1

            }) ,
            success: function (response) {
                if (response.code==200){

                } else if (response.code==2003){
                    var conf = confirm(response.messge);
                    if (conf){
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.2f3fffb417adde80&redirect_uri=http://127.0.0.1:8081/callback&state=1")
                        window.localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.messge)
                }

            },
            dataType:"json"
        }

    );
}