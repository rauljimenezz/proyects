@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Registrar devolución</h1>

    <form action="{{ route('rentals.update', $rental->rental_id) }}" method="POST">
        @csrf
        @method('PUT')

        <div class="mb-3">
            <label for="return_date" class="form-label">Fecha de devolución</label>
            <input type="date" name="return_date" id="return_date" class="form-control" value="{{ \Carbon\Carbon::now()->format('Y-m-d') }}" required>
        </div>

        <button type="submit" class="btn btn-success">Guardar</button>
        <a href="{{ route('rentals.index') }}" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
@endsection
