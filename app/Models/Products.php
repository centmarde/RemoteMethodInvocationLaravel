<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Products extends Model
{
    use HasFactory;

    protected $table = 'products'; 

    protected $primaryKey = 'product_code';

    protected $fillable = [
        'name',
        'description',
        'retail_price',
        'store_price',
        'user_id',
    ];
}
