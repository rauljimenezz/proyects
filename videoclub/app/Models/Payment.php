<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Payment extends Model
{
    use HasFactory;

    protected $table = 'payment';
    protected $primaryKey = 'payment_id';
    public $timestamps = false;

    protected $fillable = ['customer_id', 'amount', 'payment_date'];

    public function customer() {
        return $this->belongsTo(Cliente::class, 'customer_id', 'customer_id');
    }
}
