@extends('layouts.app')

@section('content')
<div class="container">
    <h1 class="mb-4 text-center">Dashboard</h1>

    <div class="row">
        <div class="col-md-6 col-lg-3 mb-4">
            <div class="card text-white bg-primary h-100">
                <div class="card-header">Total de Películas</div>
                <div class="card-body d-flex flex-column justify-content-between">
                    <h4 class="card-title">{{ $totalPeliculas }}</h4>
                    <a href="{{ route('films.index') }}" class="btn btn-light mt-3">Gestionar Películas</a>
                </div>
            </div>
        </div>

        <div class="col-md-6 col-lg-3 mb-4">
            <div class="card text-white bg-success h-100">
                <div class="card-header">Total de Clientes</div>
                <div class="card-body d-flex flex-column justify-content-between">
                    <h4 class="card-title">{{ $totalClientes }}</h4>
                    <a href="{{ route('customers.index') }}" class="btn btn-light mt-3">Gestionar Clientes</a>
                </div>
            </div>
        </div>

        <div class="col-md-6 col-lg-3 mb-4">
            <div class="card text-white bg-warning h-100">
                <div class="card-header">Alquileres Activos</div>
                <div class="card-body d-flex flex-column justify-content-between">
                    <h4 class="card-title">{{ $alquileresActivos }}</h4>
                    <a href="{{ route('rentals.index') }}" class="btn btn-light mt-3">Gestionar Alquileres</a>
                </div>
            </div>
        </div>

        <div class="col-md-6 col-lg-3 mb-4">
            <div class="card text-white bg-danger h-100">
                <div class="card-header">Alquileres Finalizados</div>
                <div class="card-body d-flex flex-column justify-content-between">
                    <h4 class="card-title">{{ $alquileresFinalizados }}</h4>
                </div>
            </div>
        </div>
    </div>
</div>
@endsection
