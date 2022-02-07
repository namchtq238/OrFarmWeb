function handleSearch(){
        var input = document.getElementById("input-search");
        var filter, ul, li, a, i;
        filter = input.value.toUpperCase();
        ul = document.getElementById("list-product");
        li = document.getElementsByClassName("single-shop-box")
        if (!filter) {
            ul.style.display = "none";
        }else{
            for (i = 0; i < li.length; i++) {
                a = li[i].getElementById("productId").getElementsByTagName("a")[0].text();
                if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    ul.style.display = "block";
                    li[i].style.display = "";
                } else {
                    li[i].style.display = "none";
                }
            }
        }
}

