import React from 'react';
import {useState, useEffect} from 'react';
import {
  FilterUseCase,
  ShipAdditional,
  ShipAdditionalServiceJS,
} from 'kmp-migration'; // can be imported directly here
import {View, Text} from 'react-native';

export function useShipAdditional() {
  const service = new ShipAdditionalServiceJS();
  const [shipAdditional, setShipAdditional] =
    useState<ReadonlyMap<string, ShipAdditional>>();
  const [filterUseCase, setFilterUseCase] = useState<FilterUseCase>();

  useEffect(() => {
    service
      .getShipAdditionalPromise()
      .then(result => {
        console.log('getShipAdditionalPromise is finished');
        // see https://kotlinlang.org/docs/js-to-kotlin-interop.html#kotlin-types-in-javascript
        setShipAdditional(result.asJsReadonlyMapView());
        setFilterUseCase(new FilterUseCase(result));
      })
      .catch(error => {
        console.error('getShipAdditionalPromise', error);
      });
  }, []);

  return {shipAdditional, filterUseCase};
}

interface ShipAdditionalListProps {
  shipAdditional: ReadonlyMap<string, ShipAdditional> | undefined;
}

function ShipAdditionalList({
  shipAdditional,
}: ShipAdditionalListProps): React.JSX.Element[] {
  if (!shipAdditional) {
    return [
      <View>
        <Text>Loading...</Text>
      </View>,
    ];
  }

  const li: React.JSX.Element[] = [];
  for (let [key, value] of shipAdditional) {
    const keyString = key.toString() + value.sigma;
    li.push(
      <View key={keyString} style={{padding: 8}}>
        <Text>{keyString}</Text>
        <Text>{value.toString()}</Text>
      </View>,
    );
  }
  return li;
}

export default ShipAdditionalList;
