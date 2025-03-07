<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Direccion extends Model
{
    use HasFactory;

    protected $table = 'address';
    protected $primaryKey = 'address_id';

    protected $fillable = ['address', 'city', 'postal_code', 'country'];
}
