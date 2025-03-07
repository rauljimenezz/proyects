@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Gestión de clientes</h1>

    <form method="GET" action="{{ route('customers.index') }}" class="mb-3">
        <div class="input-group">
            <input type="text" name="search" class="form-control" placeholder="Buscar por nombre o apellido" value="{{ request()->search }}">
            <button class="btn btn-primary" type="submit">Buscar</button>
        </div>
    </form>

    <a href="{{ route('customers.create') }}" class="btn btn-success mb-3">Añadir cliente</a>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Email</th>
                <th>Direccion</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            @foreach ($customers as $customer)
            <tr>
                <td>{{ $customer->first_name }} {{ $customer->last_name }}</td>
                <td>{{ $customer->email }}</td>
                <td>{{ $customer->address->address ?? 'Sin dirección' }}</td>
                <td>{{ $customer->active ? 'Activo' : 'Inactivo' }}</td>
                <td>
                    <a href="{{ route('customers.edit', $customer->customer_id) }}" class="btn btn-warning btn-sm">Editar</a>
                    <form action="{{ route('customers.destroy', $customer->customer_id) }}" method="POST" style="display:inline;" onsubmit="return confirm('¿Estás seguro de eliminar este cliente?');">
                        @csrf
                        @method('DELETE')
                        <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                    </form>
                </td>
            </tr>
            @endforeach
        </tbody>
    </table>

    <a href="{{ url('/') }}" class="btn btn-primary">Volver al inicio</a>
</div>
@endsection
