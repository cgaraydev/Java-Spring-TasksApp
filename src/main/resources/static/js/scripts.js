function filterTasks() {
    const searchText = document.getElementById('searchText').value.toLowerCase();
    const selectedPriority = document.getElementById('filterPriority').value;
    const selectedStatus = document.getElementById('filterStatus').value;
    const taskCards = document.querySelectorAll('.task-card');

    taskCards.forEach(card => {
        const title = card.querySelector('.card-title').textContent.toLowerCase();
        const description = card.querySelector('.card-text').textContent.toLowerCase();
        const priority = card.querySelector('.badge.bg-danger, .badge.bg-warning, .badge.bg-success')?.textContent.trim();
        const status = card.querySelector('[data-status]')?.getAttribute('data-status');

        const matchesText = title.includes(searchText) || description.includes(searchText);
        const matchesPriority = !selectedPriority || priority === selectedPriority;
        const matchesStatus = !selectedStatus || status === selectedStatus;

        if (matchesText && matchesPriority && matchesStatus) {
            card.classList.remove('hidden');
        } else {
            card.classList.add('hidden');
        }
    });
}

document.addEventListener('DOMContentLoaded', function () {
    const deleteButtons = document.querySelectorAll('.delete-task-btn');

    deleteButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();

            const taskId = button.getAttribute('href').split('/').pop(); // Obtener el ID de la tarea

            Swal.fire({
                title: '¿Estás seguro?',
                text: "¡No podrás revertir esto!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, eliminar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    fetch(`/delete-task/${taskId}`, { method: 'POST' })
                        .then(response => {
                        if (response.ok) {
                            button.closest('.task-card').remove(); // Eliminar la tarjeta de la tarea sin recargar
                            Swal.fire('Eliminado', 'La tarea ha sido eliminada.', 'success');
                        } else {
                            Swal.fire('Error', 'No se pudo eliminar la tarea.', 'error');
                        }
                    })
                        .catch(error => {
                        Swal.fire('Error', 'Ocurrió un error inesperado.', 'error');
                    });
                }
            });
        });
    });
});