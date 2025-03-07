<?php

namespace App\Http\Controllers;

use App\Models\Cliente;
use Illuminate\Http\Request;
use App\Models\Direccion;

class ClienteController extends Controller
{
    public function index(Request $request){
        $query = Cliente::with('address');
    
        if ($request->has('search')) {
            $search = $request->input('search');
            $query->where(function($q) use ($search) {
                $q->where('first_name', 'like', "%$search%")
                  ->orWhere('last_name', 'like', "%$search%");
            });
        }
    
        $customers = $query->get();
    
        return view('customers.index', compact('customers'));
    }
    

    public function create(){
        $addresses = Direccion::all();
        return view('customers.create', compact('addresses'));

    }    

    public function store(Request $request){
        $request->validate([
            'first_name' => 'required',
            'last_name' => 'required',
            'email' => 'required|email',
            'address_id' => 'required|exists:address,address_id',
            'store_id' => 'required|in:1,2',
            'active' => 'required|boolean',
        ]);
    
        Cliente::create([
            'first_name' => $request->first_name,
            'last_name' => $request->last_name,
            'email' => $request->email,
            'address_id' => $request->address_id,
            'store_id' => $request->store_id,
            'active' => $request->active,
        ]);
    
        return redirect()->route('customers.index')->with('success', 'Cliente creado');
    }
    

    public function edit($id) {
        $customer = Cliente::findOrFail($id);
        $addresses = Direccion::all();
        return view('customers.edit', compact('customer', 'addresses'));
    }
    
    public function update(Request $request, $id) {
        $request->validate([
            'first_name' => 'required',
            'last_name' => 'required',
            'email' => 'required|email',
            'address_id' => 'required|exists:address,address_id',
            'store_id' => 'required|integer',
            'active' => 'required|boolean',
        ]);
    
        $customer = Cliente::findOrFail($id);
        $customer->update($request->all());
    
        return redirect()->route('customers.index')->with('success', 'Cliente actualizado');
    }
    
    public function destroy($id) {
        $customer = Cliente::findOrFail($id);
    
        $customer->rentals()->delete();
    
        $customer->payments()->delete();
    
        $customer->delete();
    
        return redirect()->route('customers.index')->with('success', 'Cliente eliminado correctamente.');
    }
        
}
