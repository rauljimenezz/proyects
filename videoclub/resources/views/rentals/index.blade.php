@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Gestión de Alquileres</h1>
    
    <form method="GET" action="{{ route('rentals.index') }}" class="mb-3">
        <div class="row">
            <div class="col-md-4">
                <label for="customer_id" class="form-label">Filtrar por cliente</label>
                <select name="customer_id" id="customer_id" class="form-select">
                    <option value="">Seleccione un cliente</option>
                    @foreach ($customers as $customer)
                        <option value="{{ $customer->customer_id }}" {{ request('customer_id') == $customer->customer_id ? 'selected' : '' }}>
                            {{ $customer->first_name }} {{ $customer->last_name }}
                        </option>
                    @endforeach
                </select>
            </div>

            <div class="col-md-4">
                <label for="status" class="form-label">Estado del alquiler</label>
                <select name="status" id="status" class="form-select">
                    <option value="">Seleccione un estado</option>
                    <option value="active" {{ request('status') == 'active' ? 'selected' : '' }}>Activo</option>
                    <option value="returned" {{ request('status') == 'returned' ? 'selected' : '' }}>Devuelto</option>
                </select>
            </div>

            <div class="col-md-4">
                <button type="submit" class="btn btn-primary mt-4">Aplicar Filtro</button>
            </div>
        </div>
    </form>

    <a href="{{ route('rentals.create') }}" class="btn btn-primary mb-3">Nuevo Alquiler</a>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>Cliente</th>
                <th>Película</th>
                <th>Fecha de Alquiler</th>
                <th>Fecha de Devolución</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
        @foreach ($rentals as $rental)
        <tr>
            <td>{{ $rental->customer->first_name }} {{ $rental->customer->last_name }}</td>
            <td>{{ $rental->inventory->film->title }}</td>
            <td>{{ \Carbon\Carbon::parse($rental->rental_date)->format('d-m-Y') }}</td>
            <td>
                {{ $rental->return_date ? \Carbon\Carbon::parse($rental->return_date)->format('d-m-Y') : 'No devuelta' }}
            </td>
            <td>
                @if (!$rental->return_date)
                    <a href="{{ route('rentals.edit', $rental->rental_id) }}" class="btn btn-warning">Registrar Devolución</a>
                @else
                    <span class="text-success">Devuelta</span>
                @endif

                <form action="{{ route('rentals.destroy', $rental->rental_id) }}" method="POST" style="display:inline;">
                    @csrf
                    @method('DELETE')
                    <button type="submit" class="btn btn-danger" onclick="return confirm('¿Estás seguro de que deseas eliminar este alquiler?')">Eliminar</button>
                </form>
            </td>
        </tr>
        @endforeach
        </tbody>
    </table>

    <a href="{{ url('/') }}" class="btn btn-secondary mb-3">Volver al inicio</a>
</div>
@endsection