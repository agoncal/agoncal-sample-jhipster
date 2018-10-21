import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { NoautocompleteSharedLibsModule, NoautocompleteSharedCommonModule, NoaLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [NoautocompleteSharedLibsModule, NoautocompleteSharedCommonModule],
    declarations: [NoaLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [NoaLoginModalComponent],
    exports: [NoautocompleteSharedCommonModule, NoaLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NoautocompleteSharedModule {}
