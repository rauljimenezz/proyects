@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Registrar nuevo alquiler</h1>

    <form action="{{ route('rentals.store') }}" method="POST">
        @csrf
        <div class="mb-3">
            <label for="customer_id" class="form-label">Cliente</label>
            <select name="customer_id" id="customer_id" class="form-select" required>
                <option value="">Seleccione un cliente</option>
                @foreach ($customers as $customer)
                    <option value="{{ $customer->customer_id }}">{{ $customer->first_name }} {{ $customer->last_name }}</option>
                @endforeach
            </select>
        </div>

        <div class="mb-3">
            <label for="inventory_id" class="form-label">Película</label>
            <select name="inventory_id" id="inventory_id" class="form-select" required>
                <option value="">Seleccione una Película</option>
                @foreach ($inventories as $inventory)
                    <option value="{{ $inventory->inventory_id }}">
                        {{ $inventory->film->title ?? 'Título no disponible' }}
                    </option>
                @endforeach
            </select>
        </div>

        <div class="mb-3">
            <label for="rental_date" class="form-label">Fecha de alquiler</label>
            <input type="date" name="rental_date" id="rental_date" class="form-control" value="{{ old('rental_date', date('Y-m-d')) }}" required>
        </div>

        <div class="mb-3">
            <label for="staff_id" class="form-label">Empleado</label>
            <select name="staff_id" id="staff_id" class="form-select" required>
                <option value="">Seleccione un Empleado</option>
                @foreach ($staff as $employee)
                    <option value="{{ $employee->staff_id }}">{{ $employee->first_name }} {{ $employee->last_name }}</option>
                @endforeach
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Registrar Alquiler</button>
    </form>
</div>
@endsection
