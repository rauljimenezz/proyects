@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Editar Cliente</h1>

    @if ($errors->any())
        <div class="alert alert-danger">
            <ul>
                @foreach ($errors->all() as $error)
                    <li>{{ $error }}</li>
                @endforeach
            </ul>
        </div>
    @endif

    <form action="{{ route('customers.update', $customer->customer_id) }}" method="POST">
        @csrf
        @method('PUT') <!-- Esto es necesario para hacer una actualización -->
        
        <div class="mb-3">
            <label for="first_name" class="form-label">Nombre</label>
            <input type="text" class="form-control" id="first_name" name="first_name" value="{{ old('first_name', $customer->first_name) }}" required>
        </div>

        <div class="mb-3">
            <label for="last_name" class="form-label">Apellido</label>
            <input type="text" class="form-control" id="last_name" name="last_name" value="{{ old('last_name', $customer->last_name) }}" required>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Correo Electrónico</label>
            <input type="email" class="form-control" id="email" name="email" value="{{ old('email', $customer->email) }}" required>
        </div>

        <div class="mb-3">
            <label for="address_id" class="form-label">Dirección</label>
            <select class="form-control" id="address_id" name="address_id" required>
                <option value="">Seleccione una dirección</option>
                @foreach ($addresses as $address)
                    <option value="{{ $address->address_id }}" {{ $address->address_id == old('address_id', $customer->address_id) ? 'selected' : '' }}>
                        {{ $address->address }}
                    </option>
                @endforeach
            </select>
        </div>

        <div class="mb-3">
            <label for="store_id" class="form-label">ID de Tienda</label>
            <select class="form-control" id="store_id" name="store_id" required>
                <option value="1" {{ $customer->store_id == 1 ? 'selected' : '' }}>Tienda 1</option>
                <option value="2" {{ $customer->store_id == 2 ? 'selected' : '' }}>Tienda 2</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="active" class="form-label">Estado</label>
            <select class="form-control" id="active" name="active" required>
                <option value="1" {{ $customer->active == 1 ? 'selected' : '' }}>Activo</option>
                <option value="0" {{ $customer->active == 0 ? 'selected' : '' }}>Inactivo</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Actualizar Cliente</button>
        <a href="{{ route('customers.index') }}" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
@endsection
