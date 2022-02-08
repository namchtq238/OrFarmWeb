var url = 'http://localhost:8080/test'
const option = {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
    },
};
fetch(url, option)
    .then(function (res) {
        return res.json();
    })
    .then(function (products) {
        let htmls = products.map(function (product) {
            return `<li>
        ${product.id}
        </li>`;
        })
        var html = htmls.join('');
        document.getElementById('post-block').innerHTML = html;
    })