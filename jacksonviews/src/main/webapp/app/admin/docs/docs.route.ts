import { Route } from '@angular/router';

import { JacDocsComponent } from './docs.component';

export const docsRoute: Route = {
    path: 'docs',
    component: JacDocsComponent,
    data: {
        pageTitle: 'global.menu.admin.apidocs'
    }
};
