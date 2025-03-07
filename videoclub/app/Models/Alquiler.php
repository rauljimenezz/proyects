<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Alquiler extends Model
{
    use HasFactory;

    protected $table = 'rental';

    protected $primaryKey = 'rental_id';

    public $timestamps = false;

    protected $fillable = [
        'customer_id',
        'inventory_id',
        'rental_date',
        'staff_id',
    ];

    public function customer()
    {
        return $this->belongsTo(Cliente::class, 'customer_id', 'customer_id');
    }

    public function inventory()
    {
        return $this->belongsTo(Inventario::class, 'inventory_id', 'inventory_id');
    }

    public function staff()
    {
        return $this->belongsTo(Staff::class, 'staff_id', 'staff_id');
    }
}
