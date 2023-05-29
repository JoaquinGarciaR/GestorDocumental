import { createContext } from "react";
import { useState } from "react";



export const Provider = (props) => {
    const [state, setState] = useState({});

    return(
        <SelectsContext.Provider value={[state, setState]}>
            {props.children}
        </SelectsContext.Provider>
    )
}

export default Provider;
export const SelectsContext = createContext({});