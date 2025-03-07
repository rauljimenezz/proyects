<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Idioma extends Model
{
    use HasFactory;

    protected $table = 'language';
    protected $primaryKey = 'language_id';
    public $timestamps = false;

    protected $fillable = ['name'];

    public function films(){
        return $this->hasMany(Pelicula::class, 'language_id', 'language_id');
    }
}
