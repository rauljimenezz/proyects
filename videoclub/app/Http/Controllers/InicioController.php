<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Pelicula;
use App\Models\Cliente;
use App\Models\Alquiler;

class InicioController extends Controller
{
    public function index(){
        $totalPeliculas = Pelicula::count();
        $totalClientes = Cliente::count();
        $alquileresActivos = Alquiler::whereNull('return_date')->count();
        $alquileresFinalizados = Alquiler::whereNotNull('return_date')->count();

        return view('inicio', compact('totalPeliculas', 'totalClientes', 'alquileresActivos', 'alquileresFinalizados'));
    }
}
