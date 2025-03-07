<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\InicioController;
use App\Http\Controllers\PeliculaController;
use App\Http\Controllers\ClienteController;
use App\Http\Controllers\AlquilerController;

Route::get('/', function () {
    return view('welcome');
});

Route::get('/', [InicioController::class, 'index'])->name('inicio');

Route::resource('films', PeliculaController::class);
Route::resource('customers', ClienteController::class);
Route::resource('rentals', AlquilerController::class);

