window.onload = function () {
    var tipoPresencaSelect = document.querySelector('select[name="tipoPresenca"]');
    var salaIdSelect = document.querySelector('div[name="salaShow"]');

    tipoPresencaSelect.addEventListener('change', function () {
        if (this.value === 'REMOTA') {
            salaIdSelect.style.display = 'none';
        } else if (this.value === 'PRESENCIAL') {
            salaIdSelect.style.display = 'block';
        }
    });
}
