<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Pelicula extends Model
{
    use HasFactory;

    protected $table = 'film';
    protected $primaryKey = 'film_id';
    public $timestamps = false;

    protected $fillable = [
        'title', 'description', 'release_year', 'rental_duration', 'rental_rate', 'length', 'language_id'
    ];

    public function categories(){
        return $this->belongsToMany(Categoria::class, 'film_category', 'film_id', 'category_id');
    }
}
    