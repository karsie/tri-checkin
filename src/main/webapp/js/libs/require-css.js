function requireCss(url) {
    var $link = $(document.createElement("link"));
    $link.attr("type", "text/css").attr("rel", "stylesheet").attr("href", contextRoot + "/css/" + url + ".css");
    $("head").append($link);
}