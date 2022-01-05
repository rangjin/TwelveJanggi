<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script>
    function getParam(param) {
        let params = location.search.substr(location.search.indexOf("?") + 1);
        let val = "";
        params = params.split("&");

        let temp;
        for (let i = 0; i < params.length; i++) {
            temp = params[i].split("=");

            if (temp[0] === param) {
                val = temp[1];
            }
        }

        return val;
    }

    let getToken = function () {
        $.ajax({
            type: 'POST',
            url: 'https://kauth.kakao.com/oauth/token',
            dataType: "json",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            data: {
                "grant_type": "authorization_code",
                "client_id": "ec3d4717b58836334895e625f5278763",
                "redirect_uri": "http://localhost:8080/oauth/kakao/redirect",
                "code": getParam("code"),
            },
            success: function (list) {
                console.log(list);
            },
            error: function (error) {
                console.log(error);
            }
        })
    }

    getToken();

</script>