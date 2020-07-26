function mycollapse(e) {
    var id = e.getAttribute("data-id");

}

// 提交数据
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2target(questionId, 1, commentContent);

}
/**
 * 展开二级评论
 */
function collapseComments(e) {
    debugger
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data, function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        // "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function comment(e) {
    var targetId=e.getAttribute("data-id");
    var content=$("#input-"+targetId).val();
    comment2target(targetId,2,content);
}

function comment2target(targetId, type, content) {
    var questionId = targetId;
    var commentContent = content;
    if (!commentContent) {
        alert("不能回复空内容");
        return;
    }
    $.ajax(
        {
            type: "POST",
            contentType: 'application/json;charset=UTF-8',
            url: "/comment",
            data: JSON.stringify({
                "parentId": questionId,
                "content": commentContent,
                "type": type

            }),
            success: function (response) {
                if (response.code == 200) {

                    window.location.reload();
                } else if (response.code == 2003) {
                    var conf = confirm(response.messge);
                    if (conf) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.2f3fffb417adde80&redirect_uri=http://127.0.0.1:8081/callback&state=1")
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.messge)
                }

            },
            dataType: "json"
        }
    );
}
function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}