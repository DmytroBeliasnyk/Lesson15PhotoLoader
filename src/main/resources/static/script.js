document.addEventListener("DOMContentLoaded", function () {
    const imagesTable = document.getElementById('imagesTable').getElementsByTagName('tbody')[0];
    const paginationContainer = document.querySelector('.pagination');

    let currentPage = 0;
    let totalPages = 0;
    let currentCatalog = null;

    function fetchImages(catalog = currentCatalog, page = 0) {
        const url = catalog ? `/catalog?name=${catalog}&page=${page}` : `/images?page=${page}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                imagesTable.innerHTML = '';
                data.forEach(image => {
                    const row = imagesTable.insertRow();
                    const nameCell = row.insertCell(0);
                    nameCell.textContent = image.name;

                    const imageCell = row.insertCell(1);
                    const img = document.createElement('img');
                    img.src = 'data:image/png;base64,' + image.base64String;
                    imageCell.appendChild(img);
                });

                currentPage = page;
                currentCatalog = catalog;
            })
            .catch(error => console.error('Error fetching images:', error));
    }

    function fetchPageCountAndImages(catalog = currentCatalog) {
        const url = catalog ? `/pages?catalog=${catalog}` : `/pages`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                const pageCount = data.pageCount;
                const pageSize = data.pageSize;

                totalPages = Math.floor(pageCount / pageSize);
                if (pageCount % pageSize !== 0) {
                    totalPages += 1;
                }

                setupPagination();
                fetchImages(catalog, currentPage);
            })
            .catch(error => console.error('Error fetching page count:', error));
    }

    function setupPagination() {
        paginationContainer.innerHTML = '';
        for (let i = 0; i < totalPages; i++) {
            const button = document.createElement('button');
            button.textContent = i + 1;
            if (i === currentPage) {
                button.classList.add('active');
            }
            button.addEventListener('click', () => fetchImages(currentCatalog, i));
            paginationContainer.appendChild(button);
        }
    }

    document.querySelectorAll('.dropdown-content a').forEach(item => {
        item.addEventListener('click', (event) => {
            event.preventDefault();
            const catalog = item.getAttribute('data-catalog');
            fetchImages(catalog);
        });
    });

    fetchPageCountAndImages();
});
