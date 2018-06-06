// Quantity.java
// Author: Chris Stone
// CS 60

// =======================================================
//                   SAMPLE SOLUTION
// FOR PERSONAL USE ONLY --- DO NOT PRINT OR DISTRIBUTE
// =======================================================

package hw8solutions;

import java.text.DecimalFormat;     // get DecimalFormat
import java.util.*;    // get ArrayList, Collections, etc.

class Quantity extends Object {
    
    // Fields:
    //    i.e., what data is in every quantity object,
    //          and which classes can directly access it?
    
    private double mag;    // The magnitude (pos or negative)
    private double unc;    // The (absolute) uncertainty
    private Map<String,Integer> units;  // The units with exponents
    
    // Constructors:
    //    i.e., How can we build brand-new quantities?
    
    // We are allowed to have many constructors, all with
    //     the same name, as long as they take different
    //     numbers and/or types of arguments.
    
    // Constructor
    //     Takes a value; and uncertainty; and
    //     the units in the numerator and denominator
    //         (possibly with repetitions if a unit
    //         is raised to a power)
    public Quantity(double value,
                    double uncertainty,
                    List<String> numerators,
                    List<String> denominators)
    {
        if ( numerators == null ) {
            throw new IllegalArgumentException( "Numerator unit list cannot be null" );
        }
        if ( denominators == null ) {
            throw new IllegalArgumentException( "Denominator unit list cannot be null" );
        }
        
        this.mag = value;
        this.unc = uncertainty;
        this.units = new HashMap<String,Integer>();
        
        // Loop through the numerators, and increase
        //   the exponent every time we see a unit.
        for (String unitName: numerators)
            adjustExponentBy(unitName, 1);
        
        // Loop through the numerators, and decrease
        //   the exponent every time we see a unit.
        for (String unitName: denominators)
            adjustExponentBy(unitName, -1);
    }
    
    // Constructor
    //     Takes a value and the units in the numerator and denominator
    //         (possibly with repetitions if a unit
    //         is raised to a power)
    public Quantity(double value,
                    List<String> numerators,
                    List<String> denominators)
    {
        this(value, 0.0, numerators, denominators);
    }
    // Constructor
    //     Takes a value and an uncertainty;
    //        but assumes that there are no units.
    //     (Not required, but I found it handy.)
    public Quantity(double value, double uncertainty)
    {
        this.mag   = value;
        this.unc   = uncertainty;
        this.units = new HashMap<String,Integer>();
    }
    
    
    // Constructor
    //     Takes a value; assumes uncertainty is 0
    //       and that there are no units.
    //     (Not required, but I found it handy.)
    public Quantity(double mag)
    {
        // In Java, one constructor can start by calling
        //   another constructor.
        //
        // So far, we've just seen calls to "super" (which runs
        //   a constructor from the class we are inheriting from).
        //
        // We haven't mentioned this in class, but we can
        //   call another constructor in the *same* class by saying
        //   this(...) instead of super(...).
        
        this(mag, 0.0); // The previous constructor does the work,
        //   now that we've filled in the uncertainty.
    }
    
    // Constructor
    //     Assumes the defaults of value 1, uncertainty 0,
    //        and no units.
    public Quantity()
    {
        
        this(1.0);  // Calls the previous constructor
    }
    
    
    // Constructor
    //     Makes a copy of a preexisting quantity, with
    //       its own value, uncertainty, and map.
    public Quantity(Quantity other)
    {
        if (other == null ) {
            throw new IllegalArgumentException( "Quantity to copy cannot be null" );
        }
        this.mag = other.mag;
        this.unc = other.unc;
        
        // Note: If we said
        //            this.units = other.units,
        //       then both quantities would share the same
        //       map object, and any adjustment in one would
        //       affect the other.  So here, we make a
        //       *new* map that contains all the keys and values
        //       from the old map.
        
        this.units = new HashMap<String,Integer>(other.units);
    }
    
    public Quantity(double mag, double unc,
                    Map<String,Integer> exponents)
    {
        this.mag = mag;
        this.unc = unc;
        this.units = new HashMap<String,Integer>(exponents);
    }
    
    //=========================================================//
    
    // Methods
    //    i.e., what can we do with a quantity object,
    //          and who is allowed to invoke these methods?
    
    // private helper method adjustExponentBy
    //    Adds delta to the exponent of a given unit in the map.
    //    Exponents of units not in the map are assumed to start at 0.
    //    If the resulting exponent becomes 0, remove the unit
    //       from the map.
    private void adjustExponentBy(String unitName, int delta)
    {
        if ( delta == 0 ) {
            return;  // no change needed, don't add a 0
        }
        if (this.units.containsKey(unitName)) {
            int oldExponent = units.get(unitName);
            int newExponent = oldExponent + delta;
            if (newExponent == 0)
                units.remove(unitName);
            else
                units.put(unitName, newExponent);
        } else {
            units.put(unitName, delta);
        }
    }
    
    // ARITHMETIC
    
    // public method add
    //    Adds this to another quantity, to create
    //       a brand new quantity containing the sum.
    public Quantity add(Quantity other)
    {
        if (other == null) throw new IllegalArgumentException("Cannot add to null");
        
        // Create an "empty" quantity that we can fill in.
        Quantity answer = new Quantity();
        
        // Add the values
        answer.mag = this.mag + other.mag;
        
        // Error propagation formula for sums
        answer.unc = Math.sqrt(Math.pow(this.unc, 2)
                               + Math.pow(other.unc, 2));
        
        // Check that the units matched.
        if (this.units.equals(other.units))
            // If so, give the new quantity a new map
            answer.units = new HashMap<String,Integer>(this.units);
        else
            throw new IllegalArgumentException("Unit mismatch in addition");
        
        return answer;
    }
    
    // private helper method negate
    //   Returns a brand-new quantity equal to this,
    //      except the value has the opposite sign.
    //   (Not required, but I found it useful.)
    //   (In fact, we might even want to make it public
    //   because it seems like something generally useful.)
    public Quantity negate()
    {
        Quantity answer = new Quantity(this);
        
        // Negate the value
        answer.mag = -answer.mag;
        
        return answer;
    }
    
    // public method sub
    //    Subtracts another quantity from this, to create
    //       a brand new quantity containing the difference.
    public Quantity sub(Quantity other)
    {
        if (other == null) throw new IllegalArgumentException("Cannot subtract null");
        
        // this - other == this + (-other)
        return this.add(other.negate());
    }
    
    // private helper method relativeError
    //    The uncertainty in the object is called the
    //      "absolute" error, because it estimates
    //      a bound for how far the true value is
    //      from our value.
    //    Another way of measuring this is
    //      "relative" error: the absolute error
    //      divided by the magnitude.
    //    So,
    //         1,000,000 +/- 1
    //    and
    //         2 +/- 1
    //    have the same absolute error,
    //    but the relative error of the former
    //    is much smaller.
    //
    //    (Not required, but I found it useful)
    private double relativeError()
    {
        return (this.unc / this.mag);
    }
    
    // public method mul
    //    Multiplies this by another quantity, to create
    //       a brand new quantity containing the product.
    public Quantity mul(Quantity other) throws IllegalArgumentException
    {
        if (other == null) throw new IllegalArgumentException("Cannot mul by null");
        // Create a blank quantity to hold the anwer.
        Quantity answer = new Quantity();
        
        // Set the value.
        answer.mag = this.mag * other.mag;
        
        // Error propagation
        answer.unc = answer.mag
        * Math.sqrt(Math.pow(this.relativeError(), 2)
                    + Math.pow(other.relativeError(), 2));
        
        // Combine the units by making a copy of
        //   one quantity's (this's) units, and merging in
        //   the units of the other.
        answer.units = new HashMap<String,Integer>(this.units);
        
        for (String ou : other.units.keySet())
            answer.adjustExponentBy(ou, other.units.get(ou));
        
        // Done.
        return answer;
    }
    
    // public method mul
    //    Divides this by another quantity, to create
    //       a brand new quantity containing the product.
    public Quantity div(Quantity other)
    {
        if (other == null) throw new IllegalArgumentException("Cannot div by null");
        if (other.mag == 0.0) throw new IllegalArgumentException("Cannot div by 0.0");
        // Create a blank quantity to hold the answer.
        Quantity answer = new Quantity();
        
        // Find the value
        answer.mag = this.mag / other.mag;
        
        // Error propagation
        answer.unc = answer.mag
        * Math.sqrt(Math.pow(this.relativeError(), 2)
                    + Math.pow(other.relativeError(), 2));
        
        // Combine the units by making a copy of
        //   one quantity's (this's) units, and merging in
        //   the units of the other (remembering to
        //   add a - sign, since we're doing division)
        answer.units = new HashMap<String,Integer>(this.units);
        
        for (String ou : other.units.keySet())
            answer.adjustExponentBy(ou, -other.units.get(ou));
        
        // Done.
        return answer;
    }
    
    // public method mul
    //    Raises this to a power, to create
    //       a brand new quantity.
    public Quantity pow(int power)
    {
        // Create a blank quantity to hold the answer.
        Quantity answer = new Quantity();
        
        // Find the value
        answer.mag = Math.pow(this.mag, power);
        
        // Error propagation for exponentiation
        //  (which is different than for repeated multiplication!)
        answer.unc = answer.mag * Math.abs(power) * (this.unc / this.mag);
        
        // Scale the exponents of the units by the power.
        //   Java won't let us loop over a map while
        //   adjusting it, so instead we loop over this's
        //   units, and augment an initially empty map.
        answer.units = new HashMap<String,Integer>();
        
        for (String ou : this.units.keySet()) {
            if ( power * this.units.get(ou) != 0 ) {
                answer.units.put(ou, power * this.units.get(ou));
            }
        }
        
        return answer;
    }
    
    // public method toString
    //   Converts the quantity to a nice String representation.
    // UNCERTAINTY DISABLED FOR NOW
    public String toString()
    {
        double valueOfTheQuantity = this.mag;
        double uncertaintyOfTheQuantity = this.unc;
        Map<String,Integer> mapOfTheQuantity = this.units;
        
        // Ensure we get the units in order
        TreeSet<String> orderedUnits =
        new TreeSet<String>(mapOfTheQuantity.keySet());
        
        StringBuffer unitsString = new StringBuffer();
        
        for (String k : orderedUnits) {
            int expt = mapOfTheQuantity.get(k);
            unitsString.append(" " + k);
            if (expt != 1)
                unitsString.append("^" + expt);
        }
        
        // Used to convert doubles to a string with a
        //   fixed maximum number of decimal places.
        DecimalFormat df = new DecimalFormat("0.0####");
        
        // Put it all together and return.
        return df.format(valueOfTheQuantity)
        // don't print the uncertainty for now
        //+ " ~ "
        //+ df.format(uncertaintyOfTheQuantity)
        + unitsString.toString();
        
    }
    
    // public method equals
    //   True for two Quantity objects that
    //   have the same string representation.
    public boolean equals(Object other)
    {
        if (other != null && other instanceof Quantity) {
            // Yes, they're both quantity objects
            Quantity otherq = (Quantity)other;
            return this.toString().equals(otherq.toString());
        }
        else
            // We're being compared with a non-quantity. Nope.
            return false;
    }
    
    // public method hashCode
    //   Turns the quantity into an integer, subject to
    //   the Java-wide constraint that equal objects
    //   return equal hashCodes.
    public int hashCode()
    {
        // Convert this to a string, and hash that.
        return this.toString().hashCode();
    }
    
    // NORMALIZATION OF QUANTITIES
    
    // public method normalize
    //    Returns a brand-new quantity that is
    //    equivalent to this, but with all units
    //    fully expanded out.
    public Quantity normalize(Map<String,Quantity> db)
    {
        // Create a unit-less copy of this.
        Quantity answer = new Quantity(mag, unc);
        
        // For every unit attached to this, normalize it, and
        //    multiply by the appropriate power.
        for (String unitName : this.units.keySet()) {
            
            // To what exponent is the unit being applied to this?
            int exponent = this.units.get(unitName);
            
            
            // If the unit appeared once in the numerator,
            //    finding the conversion factor (a normalized equivalent)
            //    would be easy.
            Quantity conversionFactor = Quantity.normalizedUnit(unitName, db);
            
            
            // But this Quantity might have the unit raised to
            //    an exponent, so we need to adjust the conversion appropriately.
            Quantity totalConversionFactor = conversionFactor.pow(exponent);
            
            // Add the final conversion for this particular unit.
            answer = answer.mul(totalConversionFactor);      
        }
        
        // Done
        return answer;
    }
    
    // public static method normalizedUnit
    //    Returns a *normalized* Quantity representing 
    //    exactly one of the given unit.
    public static Quantity 
    normalizedUnit(String unitName, Map<String,Quantity> db)
    {
        if (db.containsKey(unitName)) {
            // It's a defined unit. Get its definition, 
            //   and normalize that.
            
            Quantity definition = db.get(unitName);
            return definition.normalize(db);
        } else {
            // unitName is already primitive, so we just need
            // a Quantity representing exactly one unit of it.
            
            // The last argument to the four-argument constructor
            //   shows yet another way to get an (immutable)
            //   empty list of Strings.
            //
            //  Ideally, we'd just be able to say 
            //    Collections.emptyList()
            //  but that gives us an empty List<Object>,
            //  which has the wrong type.
            return new Quantity(1, 
                                0.0,
                                Arrays.asList(unitName),
                                Collections.<String>emptyList());
        }
    }
}
