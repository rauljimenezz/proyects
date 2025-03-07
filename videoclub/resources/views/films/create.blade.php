@extends('layouts.app')

@section('content')
<div class="container">
    <h1>{{ isset($film) ? 'Editar Película' : 'Agregar Película' }}</h1>

    <form action="{{ isset($film) ? route('films.update', $film->film_id) : route('films.store') }}" method="POST">
        @csrf
        @if(isset($film)) @method('PUT') @endif

        <input type="text" name="title" value="{{ $film->title ?? '' }}" class="form-control mb-2" placeholder="Título">
        <textarea name="description" class="form-control mb-2" placeholder="Descripción">{{ $film->description ?? '' }}</textarea>
        <input type="number" name="release_year" value="{{ $film->release_year ?? '' }}" class="form-control mb-2" placeholder="Año de lanzamiento">
        <input type="number" name="rental_duration" value="{{ $film->rental_duration ?? '' }}" class="form-control mb-2" placeholder="Duración del alquiler">
        <input type="number" step="0.01" name="rental_rate" value="{{ $film->rental_rate ?? '' }}" class="form-control mb-2" placeholder="Tarifa de alquiler">
        <input type="number" name="length" value="{{ $film->length ?? '' }}" class="form-control mb-2" placeholder="Duración en minutos">

        <select name="language_id" class="form-control mb-2">
            <option value="">Selecciona el idioma</option>
            @foreach ($languages as $language)
                <option value="{{ $language->language_id }}" {{ isset($film) && $film->language_id == $language->language_id ? 'selected' : '' }}>
                    {{ $language->name }}
                </option>
            @endforeach
        </select>

        <select name="category_id[]" class="form-control mb-2" multiple>
            <option value="">Selecciona las categorías</option>
            @foreach ($categories as $category)
                <option value="{{ $category->category_id }}" {{ isset($film) && $film->categories->contains('category_id', $category->category_id) ? 'selected' : '' }}>
                    {{ $category->name }}
                </option>
            @endforeach
        </select>

        <button type="submit" class="btn btn-primary">Guardar</button>
    </form>
</div>
@endsection
