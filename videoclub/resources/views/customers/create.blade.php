@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Añadir Nuevo Cliente</h1>

    @if ($errors->any())
        <div class="alert alert-danger">
            <ul>
                @foreach ($errors->all() as $error)
                    <li>{{ $error }}</li>
                @endforeach
            </ul>
        </div>
    @endif

    <form action="{{ route('customers.store') }}" method="POST">
        @csrf
        <div class="mb-3">
            <label for="first_name" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="first_name" name="first_name" required>
        </div>

        <div class="mb-3">
            <label for="last_name" class="form-label">Apellido</label>
            <input type="text" class="form-control" id="last_name" name="last_name" required>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Correo Electrónico</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>

        <div class="mb-3">
            <label for="address_id" class="form-label">Dirección</label>
            <select class="form-control" id="address_id" name="address_id" required>
                <option value="">Seleccione una dirección</option>
                @foreach ($addresses as $address)
                    <option value="{{ $address->address_id }}">{{ $address->address }}</option>
                @endforeach
            </select>
        </div>

        <div class="mb-3">
        <label for="store_id" class="form-label">ID de Tienda</label>
        <select class="form-control" id="store_id" name="store_id" required>
            <option value="">Seleccione una tienda</option>
            <option value="1">Tienda 1</option>
            <option value="2">Tienda 2</option>
        </select>
    </div>


        <div class="mb-3">
            <label for="active" class="form-label">Estado</label>
            <select class="form-control" id="active" name="active" required>
                <option value="1">Activo</option>
                <option value="0">Inactivo</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Guardar Cliente</button>
        <a href="{{ route('customers.index') }}" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
@endsection
