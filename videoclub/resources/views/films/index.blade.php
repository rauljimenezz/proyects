
@extends('layouts.app')

@section('content')
<div class="container">
    <h1>Gestión de Películas</h1>

    <form action="{{ route('films.index') }}" method="GET" class="mb-3">
        <div class="input-group mb-3">
            <input type="text" name="search" class="form-control" placeholder="Buscar por título o año" value="{{ request()->search }}">
            <select name="category" class="form-select">
                <option value="">Filtrar por categoría</option>
                @foreach ($categories as $category)
                    <option value="{{ $category->category_id }}" {{ request('category') == $category->category_id ? 'selected' : '' }}>{{ $category->name }}</option>
                @endforeach
            </select>
            <button class="btn btn-primary" type="submit">Buscar</button>
        </div>
    </form>

    <a href="{{ route('films.create') }}" class="btn btn-success mb-3">Agregar Película</a>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>Título</th>
                <th>Descripción</th>
                <th>Año de lanzamiento</th>
                <th>Categoría</th>
                <th>Duración</th>
                <th>Tarifa de alquiler</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            @foreach ($films as $film)
            <tr>
                <td>{{ $film->title }}</td>
                <td>{{ $film->description }}</td>
                <td>{{ $film->release_year }}</td>
                <td>
                    @foreach ($film->categories as $category)
                        {{ $category->name }}
                    @endforeach
                </td>
                <td>{{ $film->length }}</td>
                <td>{{ $film->rental_rate }}</td>
                <td>
                    <a href="{{ route('films.edit', $film->film_id) }}" class="btn btn-warning btn-sm">Editar</a>
                    <form action="{{ route('films.destroy', $film->film_id) }}" method="POST" style="display:inline;" onsubmit="return confirm('¿Eliminar película?');">
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