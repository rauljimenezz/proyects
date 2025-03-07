<?php

namespace App\Http\Controllers;

use App\Models\Pelicula;
use App\Models\Categoria;
use App\Models\Idioma;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class PeliculaController extends Controller
{
    public function index(Request $request){
    $query = Pelicula::query();

    if ($request->has('search')) {
        $searchTerm = $request->search;
        if (is_numeric($searchTerm)) {
            $query->where('release_year', $searchTerm);
        } else {
            $query->where('title', 'like', '%' . $searchTerm . '%');
        }
    }
    

    if ($request->has('category') && !empty($request->category)) {
        $query->whereHas('categories', function ($q) use ($request) {
            $q->where('category.category_id', $request->category);
        });
    }
    

    $films = $query->with('categories')->get();
    $categories = Categoria::all();

    return view('films.index', compact('films', 'categories'));
    }

    public function create(){
        $categories = Categoria::all();
        $languages = Idioma::all();
        return view('films.create', compact('categories', 'languages'));
    }


    public function store(Request $request){
        $request->validate([
            'title' => 'required',
            'release_year' => 'required|integer',
            'rental_duration' => 'required|integer',
            'rental_rate' => 'required|numeric',
            'language_id' => 'required',
            'category_id' => 'required|array'
        ]);

        $film = Pelicula::create($request->except('category_id')); 
        $film->categories()->sync($request->category_id);
        return redirect()->route('films.index')->with('success', 'Pelicula creada');
    }

    public function edit(Pelicula $film){
        $categories = Categoria::all();
        $languages = Idioma::all();
        return view('films.edit', compact('film', 'categories', 'languages'));
    }

    public function update(Request $request, Pelicula $film){
        $request->validate([
            'title' => 'required',
            'release_year' => 'required|integer',
            'rental_duration' => 'required|integer',
            'rental_rate' => 'required|numeric',
            'language_id' => 'required',
            'category_id' => 'required|array'
        ]);

        $film->update($request->except('category_id')); 
        $film->categories()->sync($request->category_id);

        return redirect()->route('films.index')->with('success', 'Pelicula actualizada');
    }

    public function destroy(Pelicula $film){
        DB::table('rental')->whereIn('inventory_id', function ($query) use ($film) {
            $query->select('inventory_id')->from('inventory')->where('film_id', $film->film_id);
        })->delete();
    
        DB::table('inventory')->where('film_id', $film->film_id)->delete();
    
        DB::table('film_actor')->where('film_id', $film->film_id)->delete();
        DB::table('film_category')->where('film_id', $film->film_id)->delete();
    
        $film->delete();
    
        return redirect()->route('films.index')->with('success', 'Pelicula eliminada');
    }


}
