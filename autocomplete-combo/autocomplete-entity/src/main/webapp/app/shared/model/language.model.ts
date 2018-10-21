export interface ILanguage {
    id?: number;
    alpha3b?: string;
    alpha2?: string;
    name?: string;
    flag32?: string;
    flag128?: string;
    activated?: boolean;
}

export class Language implements ILanguage {
    constructor(
        public id?: number,
        public alpha3b?: string,
        public alpha2?: string,
        public name?: string,
        public flag32?: string,
        public flag128?: string,
        public activated?: boolean
    ) {
        this.activated = this.activated || false;
    }
}
