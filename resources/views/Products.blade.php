
@extends('layouts.app')

@section('content')
    <div class="container">
       
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Retail Price</th>
                    <th>Store Price</th>
                </tr>
            </thead>
            <tbody>
                @foreach ($products as $products)
                    <tr>
                        <td>{{ $products->product_code }}</td>
                        <td>{{ $products->name }}</td>
                        <td>{{ $products->description }}</td>
                        <td>{{ $products->retail_price }}</td>
                        <td>{{ $products->store_price }}</td>
                    </tr>
                @endforeach
            </tbody>
        </table>
    </div>
@endsection
