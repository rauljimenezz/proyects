<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Cliente extends Model
{
    use HasFactory;

    protected $table = 'customer';
    protected $primaryKey = 'customer_id';
    public $timestamps = false;

    protected $fillable = ['first_name', 'last_name', 'email', 'address_id', 'store_id', 'active'];

    public function payments() {
        return $this->hasMany(Payment::class, 'customer_id', 'customer_id');
    }

    public function rentals() {
        return $this->hasMany(Alquiler::class, 'customer_id', 'customer_id');
    }

    public function address(){
        return $this->belongsTo(Direccion::class, 'address_id');
    }
}
