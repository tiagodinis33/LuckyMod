function redirect(version_max,version_min){
    window.open("thankyou.php?version_max="+version_max+"&version_min="+version_min, null,null,false);
}
$.getJSON('/versions/versions.json', function(data) {
        
        var text = ``
        data.versions.forEach(function(item,index){
            text = text + `<li><a onclick="redirect(${item.versionMajor},${item.versionMinor})" href="${item.download}" download>Download ${item.name}</a></li>`
        });

        document.getElementsByClassName("downloads")[0].innerHTML = text
    
    });