<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Categoria extends Model
{
    use HasFactory;

    protected $table = 'category';
    protected $primaryKey = 'category_id';
    public $timestamps = false;

    protected $fillable = ['name'];

    public function films(){
        return $this->belongsToMany(Pelicula::class, 'film_category', 'category_id', 'film_id');
    }

    public function payments() {
        return $this->hasMany(Payment::class, 'customer_id');
    }
    
}
