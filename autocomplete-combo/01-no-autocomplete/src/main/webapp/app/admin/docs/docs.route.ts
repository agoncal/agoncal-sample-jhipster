import { Route } from '@angular/router';

import { NoaDocsComponent } from './docs.component';

export const docsRoute: Route = {
    path: 'docs',
    component: NoaDocsComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
