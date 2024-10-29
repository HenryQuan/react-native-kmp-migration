type Nullable<T> = T | null | undefined
export declare interface KtList<E> /* extends Collection<E> */ {
    asJsReadonlyArrayView(): ReadonlyArray<E>;
    readonly __doNotUseOrImplementIt: {
        readonly "kotlin.collections.KtList": unique symbol;
    };
}
export declare const KtList: {
    getInstance(): {
        fromJsArray<E>(array: ReadonlyArray<E>): KtList<E>;
    };
};
export declare interface KtMap<K, V> {
    asJsReadonlyMapView(): ReadonlyMap<K, V>;
    readonly __doNotUseOrImplementIt: {
        readonly "kotlin.collections.KtMap": unique symbol;
    };
}
export declare const KtMap: {
    getInstance(): {
        fromJsMap<K, V>(map: ReadonlyMap<K, V>): KtMap<K, V>;
    };
};
export declare class FilterUseCase {
    constructor(additionalMap: KtMap<string, ShipAdditional>);
    filterSigma(sigma: number): KtList<string>;
    filterHEPen(pen: number): KtList<string>;
}
export declare class ShipAdditional {
    constructor(alphaPiercingHE: Nullable<number>, sigma: Nullable<number>, consumables: KtList<KtList<ShipConsumable>>);
    get alphaPiercingHE(): Nullable<number>;
    get sigma(): Nullable<number>;
    get consumables(): KtList<KtList<ShipConsumable>>;
    copy(alphaPiercingHE?: Nullable<number>, sigma?: Nullable<number>, consumables?: KtList<KtList<ShipConsumable>>): ShipAdditional;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<any>): boolean;
    static get Companion(): {
    };
}
export declare class ShipConsumable {
    constructor(name: string, type: string);
    get name(): string;
    get type(): string;
    copy(name?: string, type?: string): ShipConsumable;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<any>): boolean;
    static get Companion(): {
    };
}
export declare class ShipAdditionalServiceJS /* extends ShipAdditionalService */ {
    constructor();
    getShipAdditionalPromise(): Promise<KtMap<string, ShipAdditional>>;
}