const menu = document.getElementsByClassName("menu");
document.onscrool = function (e) {
    menu[0].style.setProperty("transform",`transform(0,${window.scrollY}px)`);
    
}
