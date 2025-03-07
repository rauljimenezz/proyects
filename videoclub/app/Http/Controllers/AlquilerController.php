<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Alquiler;
use App\Models\Cliente;
use App\Models\Inventario;
use App\Models\Staff;

class AlquilerController extends Controller
{
    public function index(Request $request)
{
    $query = Alquiler::with(['customer', 'inventory.film', 'staff']);

    if ($request->has('customer_id') && $request->customer_id != '') {
        $query->where('customer_id', $request->customer_id);
    }

    if ($request->has('status') && $request->status != '') {
        if ($request->status == 'active') {
            $query->whereNull('return_date');
        } else if ($request->status == 'returned') {
            $query->whereNotNull('return_date');
        }
    }

    $rentals = $query->get();

    $customers = Cliente::all();

    return view('rentals.index', compact('rentals', 'customers'));
}

    
    public function create(){
        $customers = Cliente::all();
        $inventories = Inventario::with('film')->get(); 
        $staff = Staff::all();

        return view('rentals.create', compact('customers', 'inventories', 'staff'));
    }
    
    public function store(Request $request){
        $validated = $request->validate([
            'customer_id' => 'required|exists:customer,customer_id',
            'inventory_id' => 'required|exists:inventory,inventory_id',
            'rental_date' => 'required|date',
            'staff_id' => 'required|exists:staff,staff_id'
        ]);
    
        Alquiler::create([
            'customer_id' => $validated['customer_id'],
            'inventory_id' => $validated['inventory_id'],
            'rental_date' => $validated['rental_date'],
            'staff_id' => $validated['staff_id'],
        ]);
    
        return redirect()->route('rentals.index')->with('success', 'Alquiler registrado exitosamente.');
    }
    

    public function edit($id){
        $rental = Alquiler::findOrFail($id);
        return view('rentals.return', compact('rental'));
    }

    public function update(Request $request, $id){
        $validated = $request->validate([
            'return_date' => 'required|date|after_or_equal:' . now()->toDateString(),
        ]);
    
        $rental = Alquiler::findOrFail($id);
        $rental->return_date = $validated['return_date'];
        $rental->save();
    
        return redirect()->route('rentals.index')->with('success', 'Devolución registrada correctamente.');
    }
    
    public function destroy($id){
        $rental = Alquiler::findOrFail($id);

        $rental->delete();

        return redirect()->route('rentals.index')->with('success', 'Alquiler eliminado exitosamente.');
    }

}
